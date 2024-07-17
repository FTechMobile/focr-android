package ai.ftech.focrsdk.sdk

import ai.ftech.focrsdk.R
import ai.ftech.focrsdk.extension.getAppString
import ai.ftech.focrsdk.extension.getString
import ai.ftech.focrsdk.data.local.DataStoreService
import ai.ftech.focrsdk.data.local.IDataStore
import ai.ftech.focrsdk.data.remote.GatewayService
import ai.ftech.focrsdk.data.remote.OCRService
import ai.ftech.focrsdk.data.repositories.IGatewayAPI
import ai.ftech.focrsdk.data.repositories.IOCRAPI
import ai.ftech.focrsdk.data.source.remote.GatewayRemoteDataSource
import ai.ftech.focrsdk.data.source.remote.OCRRemoteDataSource
import ai.ftech.focrsdk.domain.exceptions.AppException
import ai.ftech.focrsdk.domain.model.BaseCallBack
import ai.ftech.focrsdk.domain.model.InitGatewayRequest
import ai.ftech.focrsdk.domain.model.OCRExportType
import ai.ftech.focrsdk.domain.model.OCRRequest
import ai.ftech.focrsdk.utils.AppConstant
import ai.ftech.focrsdk.utils.DataStoreConstant
import android.app.Application
import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File

object OCRManager {
    private val TAG: String = OCRManager::class.java.simpleName
    private var applicationContext: Context? = null
    private var gateWayRemote: GatewayRemoteDataSource? = null
    private var ocrRemote: OCRRemoteDataSource? = null
    private var dataStore: IDataStore? = null
    private var ocrCallback: IOCRCallback? = null


    @JvmStatic
    fun init(context: Context) {
        applicationContext = context
        dataStore = DataStoreService(context)
        gateWayRemote = GatewayRemoteDataSource(
            context,
            GatewayService().create(IGatewayAPI::class.java))
        ocrRemote = OCRRemoteDataSource(context, OCRService(dataStore as IDataStore).create(IOCRAPI::class.java))
        runBlocking {
            dataStore?.remove(DataStoreConstant.TOKEN_KEY)
        }
    }

    fun getApplicationContext(): Application {
        return applicationContext as? Application
            ?: throw RuntimeException(getAppString(R.string.context_null_error))
    }

    @JvmStatic
    fun initGateway(appId: String, secretKey: String, callback: IInitGatewayCallback) {
        if (appId.isEmpty()) {
            callback.onFail(
                AppException(
                    statusCode = AppConstant.UNKNOWN_ERROR,
                    message = getAppString(R.string.empty_app_id)
                )
            )
            return
        }
        if (secretKey.isEmpty()) {
            callback.onFail(
                AppException(
                    statusCode = AppConstant.UNKNOWN_ERROR,
                    message = getAppString(R.string.empty_secret_key)
                )
            )
            return
        }
        val request = InitGatewayRequest().apply {
            this.appId = appId
            this.secretKey = secretKey
        }
        gateWayRemote?.let { remote ->
            CoroutineScope(Dispatchers.IO).launch {
                remote.initGateway(request).collect { result ->
                    when (result) {
                        is BaseCallBack.Success -> {
                            val token = result.data?.data?.token.getString()
                            if (token.isEmpty()) {
                                callback.onFail(AppException(getAppString(R.string.message_token_is_empty)))
                            } else {
                                dataStore?.put(DataStoreConstant.TOKEN_KEY, result.data?.data?.token.getString())
                                callback.onSuccess()
                            }
                        }

                        is BaseCallBack.Error -> {
                            callback.onFail(result.error)
                        }
                    }
                }
            }
        }
    }

    @JvmStatic
    fun getConfig(callback: IOCRConfigCallback) {
        ocrRemote?.let { remote ->
            CoroutineScope(Dispatchers.IO).launch {
                remote.getConfig().collect { result ->
                    when (result) {
                        is BaseCallBack.Success -> {
                            val data = result.data?.data ?: listOf()
                            data.forEach { configData ->
                                configData.listMode.forEach { configValue ->
                                    if (configValue.id == "normal" || configValue.id == "advance") {
                                        configValue.id = "raw"
                                    }
                                }
                            }
                            callback.onSuccess(data)
                        }

                        is BaseCallBack.Error -> {
                            callback.onFail(result.error)
                        }
                    }
                }
            }
        }
    }

    @JvmStatic
    fun registerOCRCallback(ocrCallback: IOCRCallback) {
        this.ocrCallback = ocrCallback
    }

    @JvmStatic
    fun startOCR(documentType: String, responseFormat: String, toMathMl: Boolean, file: File) {
        startOCR(documentType, responseFormat, toMathMl, file.absolutePath)
    }

    @JvmStatic
    fun startOCR(documentType: String, responseFormat: String, toMathMl: Boolean, absoluteFilePath: String) {
        if (isNotInitGateway()) {
            ocrCallback?.onFail(AppException(getAppString(R.string.message_recorder_not_initial)))
            return
        }

        if (documentType.isEmpty()) {
            ocrCallback?.onFail(AppException(getAppString(R.string.message_document_type_empty)))
            return
        }

        if (responseFormat.isEmpty()) {
            ocrCallback?.onFail(AppException(getAppString(R.string.message_response_format_empty)))
            return
        }

        if (absoluteFilePath.isEmpty()) {
            ocrCallback?.onFail(AppException(getAppString(R.string.message_file_empty)))
            return
        }

        ocrCallback?.onStart()

        val request = OCRRequest().apply {
            this.documentType = documentType
            this.responseFormat = responseFormat
            this.toMathMl = toMathMl
        }
        ocrRemote?.let { remote ->
            CoroutineScope(Dispatchers.IO).launch {
                remote.ocr(absoluteFilePath, request).collect { result ->
                    when (result) {
                        is BaseCallBack.Success -> {
                            val data = result.data?.data
                            if (result.data?.isSuccessful() == true && data != null) {
                                ocrCallback?.onSuccess(data)
                            } else {
                                ocrCallback?.onFail(AppException(result.data?.message.getString()))
                            }

                        }

                        is BaseCallBack.Error -> {
                            ocrCallback?.onFail(result.error)
                        }
                    }
                }
            }
        }
    }

    private fun isNotInitGateway(): Boolean {
        return runBlocking {
            val token = dataStore?.get(DataStoreConstant.TOKEN_KEY, "")?.getString()
            token.isNullOrEmpty()
        }
    }

    @JvmStatic
    fun export(requestID: String, type: OCRExportType, callback: IExportCallback) {
        if (requestID.isEmpty()) {
            callback.onFail(AppException(getAppString(R.string.message_request_id_empty)))
            return
        }
        ocrRemote?.let { remote ->
            CoroutineScope(Dispatchers.IO).launch {
                remote.export(requestID, type.value).collect { result ->
                    when (result) {
                        is BaseCallBack.Success -> {
                            val data = result.data?.data
                            if (result.data?.isSuccessful() == true && data != null) {
                                callback.onSuccess(data)
                            } else {
                                callback.onFail(AppException(result.data?.message.getString()))
                            }

                        }

                        is BaseCallBack.Error -> {
                            callback.onFail(result.error)
                        }
                    }
                }
            }
        }
    }
}
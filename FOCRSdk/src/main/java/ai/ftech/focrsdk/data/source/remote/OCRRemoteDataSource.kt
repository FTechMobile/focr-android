package ai.ftech.focrsdk.data.source.remote

import ai.ftech.focrsdk.data.repositories.IOCRAPI
import ai.ftech.focrsdk.data.source.OCRDataSource
import ai.ftech.focrsdk.data.source.handle
import ai.ftech.focrsdk.domain.model.BaseCallBack
import ai.ftech.focrsdk.domain.model.OCRConfigResponse
import ai.ftech.focrsdk.domain.model.OCRExportResponse
import ai.ftech.focrsdk.domain.model.OCRRequest
import ai.ftech.focrsdk.domain.model.OCRResponse
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OCRRemoteDataSource @Inject constructor(
    @ApplicationContext val context: Context,
    private val ocrAPI: IOCRAPI,
) : OCRDataSource {
    companion object {
        private const val PART_FILE = "file"
    }

    override fun ocr(filePath: String, request: OCRRequest): Flow<BaseCallBack<OCRResponse>> {
        return flow {
            val part = convertFileToMultipart(filePath)
            val documentType = (request.documentType
                ?: "").toRequestBody("text/plain".toMediaTypeOrNull())
            val responseFormat = (request.responseFormat
                ?: "").toRequestBody("text/plain".toMediaTypeOrNull())
            val toMathMl = ((request.toMathMl
                ?: true).toString()).toRequestBody("text/plain".toMediaTypeOrNull())
            val result = ocrAPI.runCatching { ocr(part, documentType, responseFormat, toMathMl) }
            emit(result.handle())
        }
    }

    override fun getConfig(): Flow<BaseCallBack<OCRConfigResponse>> {
        return flow {
            val result = ocrAPI.runCatching { getLanguageConfig() }
            emit(result.handle())
        }
    }

    override fun export(requestId: String, type: String): Flow<BaseCallBack<OCRExportResponse>> {
        return flow {
            val result = ocrAPI.runCatching { export(requestId,type) }
            emit(result.handle())
        }
    }

    private fun convertFileToMultipart(absolutePath: String): MultipartBody.Part {
        val file = File(absolutePath)
        return MultipartBody.Part.createFormData(PART_FILE, file.name, file.asRequestBody())
    }
}
package ai.ftech.focrsdk.data.remote

import ai.ftech.focrsdk.data.local.IDataStore
import ai.ftech.focrsdk.utils.AppConstant
import ai.ftech.focrsdk.utils.DataStoreConstant
import kotlinx.coroutines.runBlocking
import okhttp3.logging.HttpLoggingInterceptor

class OCRService (private val datastore: IDataStore) : NetworkServices() {
    override var baseUrl: String = AppConstant.BASE_OCR_URL
    override val headers: Map<String, String>
        get() = mapOf(
            AppConstant.HEADER_CONTENT_TYPE to AppConstant.APPLICATION_JSON,
            AppConstant.HEADER_AUTHORIZATION to runBlocking {
                String.format(AppConstant.AUTH_BEARER, datastore.get(DataStoreConstant.TOKEN_KEY, ""))
            }
        )

    override var timeout: Long = AppConstant.TIMEOUT

    override var logLevel: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BODY

    init {
        build()
    }
}
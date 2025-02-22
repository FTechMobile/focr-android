package ai.ftech.focrsdk.data.repositories

import ai.ftech.focrsdk.domain.model.OCRConfigResponse
import ai.ftech.focrsdk.domain.model.OCRExportResponse
import ai.ftech.focrsdk.domain.model.OCRResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface IOCRAPI {

    @Multipart
    @POST("ocr/transform")
    suspend fun ocr(@Part file: MultipartBody.Part,
                    @Part("document_type") documentType: RequestBody,
                    @Part("response_format") responseFormat: RequestBody,
                    @Part("to_mathml") toMathMl: RequestBody): OCRResponse

    @GET("ocr/get-config")
    suspend fun getLanguageConfig(): OCRConfigResponse

    @GET("ocr/{request_id}/export")
    suspend fun export(@Path("request_id") requestId: String, @Query("type") type: String): OCRExportResponse
}
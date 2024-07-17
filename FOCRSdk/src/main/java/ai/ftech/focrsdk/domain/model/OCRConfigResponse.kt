package ai.ftech.focrsdk.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OCRConfigResponse(
    @SerialName("data")
    var data: List<OCRConfigData> = listOf()
) : BaseResponse() {
    override fun isSuccessful(): Boolean {
        return code == 0 && data.isNotEmpty()
    }
}
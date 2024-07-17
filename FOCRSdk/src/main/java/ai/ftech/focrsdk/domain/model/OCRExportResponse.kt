package ai.ftech.focrsdk.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OCRExportResponse(
    @SerialName("data")
    var data: String?
) : BaseResponse() {
    override fun isSuccessful(): Boolean {
        return code == 0 && !data.isNullOrEmpty()
    }
}
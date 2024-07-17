package ai.ftech.focrsdk.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InitGatewayResponse(
    @SerialName("data")
    var data: InitGatewayData? = null
) : BaseResponse() {
    override fun isSuccessful(): Boolean {
        return code == 0 && data != null
    }
}
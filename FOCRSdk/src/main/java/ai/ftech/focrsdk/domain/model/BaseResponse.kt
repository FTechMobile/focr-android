package ai.ftech.focrsdk.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
abstract class BaseResponse {
    @SerialName("code")
    var code: Int? = null
    @SerialName("msg")
    var message: String? = null
    open fun isSuccessful(): Boolean = true
}
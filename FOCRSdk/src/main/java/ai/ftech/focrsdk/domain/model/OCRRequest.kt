package ai.ftech.focrsdk.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OCRRequest(
    @SerialName("document_type")
    var documentType: String? = null,
    @SerialName("response_format")
    var responseFormat: String? = null,
    @SerialName("to_mathml")
    var toMathMl: Boolean? = null,
)
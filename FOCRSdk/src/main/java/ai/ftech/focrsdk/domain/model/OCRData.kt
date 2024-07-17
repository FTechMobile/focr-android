package ai.ftech.focrsdk.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OCRData(
    @SerialName("request_id")
    var requestID: String? = null,
    @SerialName("image_url")
    var imageUrl: String? = null,
    @SerialName("transform_data")
    var transformData: TransformData? = null,
)
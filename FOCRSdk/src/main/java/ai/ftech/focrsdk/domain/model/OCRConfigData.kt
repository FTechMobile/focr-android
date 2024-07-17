package ai.ftech.focrsdk.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OCRConfigData(
    @SerialName("id")
    var id: String? = null,
    @SerialName("name")
    var name: String? = null,
    @SerialName("mode")
    val listMode: List<ORCConfigValue> = listOf()
)

@Serializable
data class ORCConfigValue(
    @SerialName("id")
    var id: String? = null,
    @SerialName("name")
    var name: String? = null,
    @SerialName("response_format")
    var responseFormat: String? = null
)
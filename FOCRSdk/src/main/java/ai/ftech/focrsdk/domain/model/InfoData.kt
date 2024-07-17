package ai.ftech.focrsdk.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InfoData(
    @SerialName("id")
    var id: Int? = null,
    @SerialName("type")
    var type: String? = null,
    @SerialName("key")
    var key: String? = null,
    @SerialName("title")
    var title: String? = null,
    @SerialName("content")
    var content: String? = null,
    @SerialName("data")
    var data: List<InfoData>? = null,
)
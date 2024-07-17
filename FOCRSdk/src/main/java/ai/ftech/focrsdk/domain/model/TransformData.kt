package ai.ftech.focrsdk.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransformData(
    @SerialName("questions")
    var questions: List<String>? = null,
    @SerialName("table_error")
    var tableError: String? = null,
    @SerialName("field_error")
    var fieldError: String? = null,
    @SerialName("record_type")
    var recordType: String? = null,
    @SerialName("data")
    var data: List<InfoData>? = null,
)
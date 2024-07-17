package ai.ftech.focrsdk.domain.exceptions

import ai.ftech.focrsdk.utils.AppConstant

data class AppException(
    override var message: String? = null,
    var statusCode: Int = AppConstant.UNKNOWN_ERROR,
): Exception()
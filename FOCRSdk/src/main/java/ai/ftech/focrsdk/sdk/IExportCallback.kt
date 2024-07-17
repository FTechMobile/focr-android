package ai.ftech.focrsdk.sdk

import ai.ftech.focrsdk.domain.exceptions.AppException

interface IExportCallback {
    fun onSuccess(data: String)

    fun onFail(error: AppException?)

}
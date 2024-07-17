package ai.ftech.focrsdk.sdk

import ai.ftech.focrsdk.domain.exceptions.AppException

interface IInitGatewayCallback {
    fun onSuccess()

    fun onFail(error: AppException?)

}
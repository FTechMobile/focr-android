package ai.ftech.focrsdk.domain.model

import ai.ftech.focrsdk.domain.exceptions.AppException

sealed class BaseCallBack<T>(val error: AppException? = null,
                             val data: T? = null) {
    class Success<T>(data: T?) : BaseCallBack<T>(data = data)
    class Error<T>(error: AppException? = null) : BaseCallBack<T>(error = error)
}


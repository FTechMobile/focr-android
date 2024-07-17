package ai.ftech.focrsdk.sdk

import ai.ftech.focrsdk.domain.exceptions.AppException
import ai.ftech.focrsdk.domain.model.OCRConfigData

interface IOCRConfigCallback {
    fun onSuccess(listConfig:List<OCRConfigData>)
    fun onFail(error: AppException?)
}
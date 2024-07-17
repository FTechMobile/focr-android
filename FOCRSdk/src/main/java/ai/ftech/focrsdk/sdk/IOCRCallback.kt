package ai.ftech.focrsdk.sdk

import ai.ftech.focrsdk.domain.exceptions.AppException
import ai.ftech.focrsdk.domain.model.OCRData
import ai.ftech.focrsdk.domain.model.TransformData

interface IOCRCallback {
    fun onStart()
    fun onFail(error: AppException?)
    fun onSuccess(result: OCRData)
}

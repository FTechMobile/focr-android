package ai.ftech.focrsdk.domain.repositories

import ai.ftech.focrsdk.domain.model.BaseCallBack
import ai.ftech.focrsdk.domain.model.OCRConfigResponse
import ai.ftech.focrsdk.domain.model.OCRExportResponse
import ai.ftech.focrsdk.domain.model.OCRRequest
import ai.ftech.focrsdk.domain.model.OCRResponse
import kotlinx.coroutines.flow.Flow

interface OCRRepo {
    fun ocr(filePath: String, request: OCRRequest): Flow<BaseCallBack<OCRResponse>>
    fun getConfig(): Flow<BaseCallBack<OCRConfigResponse>>
    fun export(requestID: String, type: String): Flow<BaseCallBack<OCRExportResponse>>
}
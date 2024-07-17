package ai.ftech.focrsdk.data.repositories

import ai.ftech.focrsdk.data.source.OCRDataSource
import ai.ftech.focrsdk.domain.model.BaseCallBack
import ai.ftech.focrsdk.domain.model.OCRConfigResponse
import ai.ftech.focrsdk.domain.model.OCRExportResponse
import ai.ftech.focrsdk.domain.model.OCRRequest
import ai.ftech.focrsdk.domain.model.OCRResponse
import ai.ftech.focrsdk.domain.repositories.OCRRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OCRRepoImpl @Inject constructor(private val ocrDataSource: OCRDataSource) : OCRRepo {

    override fun ocr(filePath: String, request: OCRRequest): Flow<BaseCallBack<OCRResponse>> {
        return ocrDataSource.ocr(filePath, request)
    }

    override fun getConfig(): Flow<BaseCallBack<OCRConfigResponse>> {
        return ocrDataSource.getConfig()
    }

    override fun export(requestID: String, type: String): Flow<BaseCallBack<OCRExportResponse>> {
        return ocrDataSource.export(requestID, type)
    }

}
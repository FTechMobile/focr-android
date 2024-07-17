package ai.ftech.focrsdk.data.source

import ai.ftech.focrsdk.domain.model.BaseCallBack
import ai.ftech.focrsdk.domain.model.InitGatewayRequest
import ai.ftech.focrsdk.domain.model.InitGatewayResponse
import kotlinx.coroutines.flow.Flow

interface GatewayDataSource {
    fun initGateway(request: InitGatewayRequest): Flow<BaseCallBack<InitGatewayResponse>>
}
package ai.ftech.focrsdk.domain.repositories

import ai.ftech.focrsdk.domain.model.BaseCallBack
import ai.ftech.focrsdk.domain.model.InitGatewayRequest
import ai.ftech.focrsdk.domain.model.InitGatewayResponse
import kotlinx.coroutines.flow.Flow

interface GatewayRepo {
    fun initGateway(request: InitGatewayRequest): Flow<BaseCallBack<InitGatewayResponse>>
}
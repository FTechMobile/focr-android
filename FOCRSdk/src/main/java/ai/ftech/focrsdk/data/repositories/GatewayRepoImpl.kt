package ai.ftech.focrsdk.data.repositories

import ai.ftech.focrsdk.data.source.GatewayDataSource
import ai.ftech.focrsdk.domain.model.BaseCallBack
import ai.ftech.focrsdk.domain.model.InitGatewayRequest
import ai.ftech.focrsdk.domain.model.InitGatewayResponse
import ai.ftech.focrsdk.domain.repositories.GatewayRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GatewayRepoImpl @Inject constructor(private val gatewayDataSource: GatewayDataSource) : GatewayRepo {
    override fun initGateway(request: InitGatewayRequest): Flow<BaseCallBack<InitGatewayResponse>> {
        return gatewayDataSource.initGateway(request)
    }

}
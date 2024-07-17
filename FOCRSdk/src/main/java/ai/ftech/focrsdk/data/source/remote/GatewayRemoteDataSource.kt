package ai.ftech.focrsdk.data.source.remote

import android.content.Context
import ai.ftech.focrsdk.data.repositories.IGatewayAPI
import ai.ftech.focrsdk.data.source.GatewayDataSource
import ai.ftech.focrsdk.data.source.handle
import ai.ftech.focrsdk.domain.model.BaseCallBack
import ai.ftech.focrsdk.domain.model.InitGatewayRequest
import ai.ftech.focrsdk.domain.model.InitGatewayResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GatewayRemoteDataSource @Inject constructor(
    @ApplicationContext val context: Context,
    private val gatewayAPI: IGatewayAPI,
) : GatewayDataSource {

    override fun initGateway(request: InitGatewayRequest): Flow<BaseCallBack<InitGatewayResponse>> {
        return flow {
            val result = gatewayAPI.runCatching { initGateway(request) }
            emit(result.handle())
        }
    }
}
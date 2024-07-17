package ai.ftech.focrsdk.di

import android.content.Context
import ai.ftech.focrsdk.data.local.DataStoreService
import ai.ftech.focrsdk.data.local.IDataStore
import ai.ftech.focrsdk.data.remote.GatewayService
import ai.ftech.focrsdk.data.remote.OCRService
import ai.ftech.focrsdk.data.repositories.IGatewayAPI
import ai.ftech.focrsdk.data.repositories.IOCRAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGatewayApi(): IGatewayAPI {
        return GatewayService().create(IGatewayAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideTTSApi(datastore: IDataStore): IOCRAPI {
        return OCRService(datastore).create(IOCRAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideDataStore(
        @ApplicationContext app: Context
    ): IDataStore = DataStoreService(app)

}
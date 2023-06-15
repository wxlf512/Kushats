package dev.wxlf.kushats.feature_main.di.modules

import dagger.Module
import dagger.Provides
import dev.wxlf.kushats.core.data.retrofit.FoodApi
import dev.wxlf.kushats.feature_main.data.FoodRepositoryImpl
import dev.wxlf.kushats.feature_main.data.datasources.remote.FoodRemoteDataSource
import dev.wxlf.kushats.feature_main.data.datasources.remote.FoodRetrofitDataSource
import dev.wxlf.kushats.feature_main.domain.FoodRepository
import javax.inject.Singleton

@Module
class MainDataModule {

    @Provides
    @Singleton
    fun provideFoodRetrofitDataSource(foodApi: FoodApi): FoodRemoteDataSource =
        FoodRetrofitDataSource(foodApi)

    @Provides
    @Singleton
    fun provideFoodRepository(foodRemoteDataSource: FoodRemoteDataSource): FoodRepository =
        FoodRepositoryImpl(foodRemoteDataSource)
}
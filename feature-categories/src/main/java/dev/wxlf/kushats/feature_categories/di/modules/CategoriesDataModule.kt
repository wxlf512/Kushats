package dev.wxlf.kushats.feature_categories.di.modules

import dagger.Module
import dagger.Provides
import dev.wxlf.kushats.core.data.retrofit.FoodApi
import dev.wxlf.kushats.core.di.NetworkModule
import dev.wxlf.kushats.feature_categories.data.FoodRepositoryImpl
import dev.wxlf.kushats.feature_categories.data.datasources.remote.FoodRemoteDataSource
import dev.wxlf.kushats.feature_categories.data.datasources.remote.FoodRetrofitDataSource
import dev.wxlf.kushats.feature_categories.domain.FoodRepository
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
class CategoriesDataModule {

    @Provides
    @Singleton
    fun provideFoodRetrofitDataSource(foodApi: FoodApi): FoodRemoteDataSource =
        FoodRetrofitDataSource(foodApi)

    @Provides
    @Singleton
    fun provideFoodRepository(foodRemoteDataSource: FoodRemoteDataSource): FoodRepository =
        FoodRepositoryImpl(foodRemoteDataSource)
}
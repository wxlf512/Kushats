package dev.wxlf.kushats.feature_categories.di.modules

import dagger.Module
import dagger.Provides
import dev.wxlf.kushats.core.data.retrofit.FoodApi
import dev.wxlf.kushats.core.di.NetworkModule
import dev.wxlf.kushats.feature_categories.data.FoodRepositoryImpl
import dev.wxlf.kushats.feature_categories.data.datasources.remote.FoodRemoteDataSource
import dev.wxlf.kushats.feature_categories.data.datasources.remote.FoodRetrofitDataSource
import dev.wxlf.kushats.feature_categories.di.CategoriesScope
import dev.wxlf.kushats.feature_categories.domain.FoodRepository

@Module(includes = [NetworkModule::class])
class DataModule {

    @Provides
    @CategoriesScope
    fun provideFoodRetrofitDataSource(foodApi: FoodApi): FoodRemoteDataSource =
        FoodRetrofitDataSource(foodApi)

    @Provides
    @CategoriesScope
    fun provideFoodRepository(foodRemoteDataSource: FoodRemoteDataSource): FoodRepository =
        FoodRepositoryImpl(foodRemoteDataSource)
}
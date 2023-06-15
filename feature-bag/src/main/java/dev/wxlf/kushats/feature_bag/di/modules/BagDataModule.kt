package dev.wxlf.kushats.feature_bag.di.modules

import dagger.Module
import dagger.Provides
import dev.wxlf.kushats.core.data.retrofit.FoodApi
import dev.wxlf.kushats.core.data.room.BagDao
import dev.wxlf.kushats.feature_bag.data.datasources.local.BagLocalDataSource
import dev.wxlf.kushats.feature_bag.data.datasources.local.BagRoomDataSource
import dev.wxlf.kushats.feature_bag.data.datasources.remote.FoodRemoteDataSource
import dev.wxlf.kushats.feature_bag.data.datasources.remote.FoodRetrofitDataSource
import dev.wxlf.kushats.feature_bag.data.repositories.BagRepositoryImpl
import dev.wxlf.kushats.feature_bag.data.repositories.FoodRepositoryImpl
import dev.wxlf.kushats.feature_bag.domain.repositories.BagRepository
import dev.wxlf.kushats.feature_bag.domain.repositories.FoodRepository
import javax.inject.Singleton

@Module
class BagDataModule {

    @Provides
    @Singleton
    fun provideFoodRetrofitDataSource(foodApi: FoodApi): FoodRemoteDataSource =
        FoodRetrofitDataSource(foodApi)

    @Provides
    @Singleton
    fun provideBagRoomDataSource(bagDao: BagDao): BagLocalDataSource =
        BagRoomDataSource(bagDao)

    @Provides
    @Singleton
    fun provideFoodRepository(remoteDataSource: FoodRemoteDataSource): FoodRepository =
        FoodRepositoryImpl(remoteDataSource)

    @Provides
    @Singleton
    fun provideBagRepository(localDataSource: BagLocalDataSource): BagRepository =
        BagRepositoryImpl(localDataSource)
}
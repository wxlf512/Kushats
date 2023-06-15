package dev.wxlf.kushats.feature_bag.data.repositories

import dev.wxlf.kushats.core.data.models.DishesModel
import dev.wxlf.kushats.feature_bag.data.datasources.remote.FoodRemoteDataSource
import dev.wxlf.kushats.feature_bag.domain.repositories.FoodRepository

class FoodRepositoryImpl(private val remoteDataSource: FoodRemoteDataSource) : FoodRepository {
    override suspend fun fetchDishes(): DishesModel = remoteDataSource.loadDishes()
}
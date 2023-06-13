package dev.wxlf.kushats.feature_catalog.data.repositories

import dev.wxlf.kushats.core.data.models.DishesModel
import dev.wxlf.kushats.feature_catalog.data.datasources.remote.FoodRemoteDataSource
import dev.wxlf.kushats.feature_catalog.domain.repositories.FoodRepository

class FoodRepositoryImpl(private val remoteDataSource: FoodRemoteDataSource) : FoodRepository {

    override suspend fun fetchDishes(): DishesModel = remoteDataSource.loadDishes()
}
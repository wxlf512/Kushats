package dev.wxlf.kushats.feature_category.data.repositories

import dev.wxlf.kushats.core.data.models.CategoriesModel
import dev.wxlf.kushats.core.data.models.DishesModel
import dev.wxlf.kushats.feature_category.data.datasources.remote.FoodRemoteDataSource
import dev.wxlf.kushats.feature_category.domain.repositories.FoodRepository

class FoodRepositoryImpl(private val remoteDataSource: FoodRemoteDataSource) : FoodRepository {

    override suspend fun fetchDishes(): DishesModel = remoteDataSource.loadDishes()
    override suspend fun fetchCategories(): CategoriesModel = remoteDataSource.loadCategories()
}
package dev.wxlf.kushats.feature_main.data

import dev.wxlf.kushats.core.data.models.CategoriesModel
import dev.wxlf.kushats.feature_main.data.datasources.remote.FoodRemoteDataSource
import dev.wxlf.kushats.feature_main.domain.FoodRepository

class FoodRepositoryImpl(private val remoteDataSource: FoodRemoteDataSource) : FoodRepository {

    override suspend fun fetchCategories(): CategoriesModel = remoteDataSource.loadCategories()
}
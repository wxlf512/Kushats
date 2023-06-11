package dev.wxlf.kushats.feature_categories.data

import dev.wxlf.kushats.core.data.models.CategoriesModel
import dev.wxlf.kushats.feature_categories.data.datasources.remote.FoodRemoteDataSource
import dev.wxlf.kushats.feature_categories.domain.FoodRepository

class FoodRepositoryImpl(private val remoteDataSource: FoodRemoteDataSource) : FoodRepository {

    override suspend fun fetchCategories(): CategoriesModel = remoteDataSource.loadCategories()
}
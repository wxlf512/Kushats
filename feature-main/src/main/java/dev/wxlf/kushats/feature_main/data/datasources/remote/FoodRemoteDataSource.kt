package dev.wxlf.kushats.feature_main.data.datasources.remote

import dev.wxlf.kushats.core.data.models.CategoriesModel

interface FoodRemoteDataSource {

    suspend fun loadCategories(): CategoriesModel
}

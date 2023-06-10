package dev.wxlf.kushats.feature_categories.data.datasources.remote

import dev.wxlf.kushats.core.data.models.CategoriesModel

interface FoodRemoteDataSource {

    suspend fun loadCategories(): CategoriesModel
}

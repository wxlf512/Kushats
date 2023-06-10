package dev.wxlf.kushats.feature_categories.domain

import dev.wxlf.kushats.core.data.models.CategoriesModel

interface FoodRepository {

    suspend fun fetchCategories(): CategoriesModel
}

package dev.wxlf.kushats.feature_catalog.domain.repositories

import dev.wxlf.kushats.core.data.models.CategoriesModel
import dev.wxlf.kushats.core.data.models.DishesModel

interface FoodRepository {

    suspend fun fetchDishes(): DishesModel
    suspend fun fetchCategories(): CategoriesModel
}
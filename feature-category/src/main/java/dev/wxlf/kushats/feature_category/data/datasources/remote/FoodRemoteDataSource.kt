package dev.wxlf.kushats.feature_category.data.datasources.remote

import dev.wxlf.kushats.core.data.models.CategoriesModel
import dev.wxlf.kushats.core.data.models.DishesModel


interface FoodRemoteDataSource {

    suspend fun loadDishes(): DishesModel
    suspend fun loadCategories(): CategoriesModel
}

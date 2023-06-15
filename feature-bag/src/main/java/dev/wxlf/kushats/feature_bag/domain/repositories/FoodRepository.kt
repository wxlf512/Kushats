package dev.wxlf.kushats.feature_bag.domain.repositories

import dev.wxlf.kushats.core.data.models.DishesModel

interface FoodRepository {

    suspend fun fetchDishes(): DishesModel
}
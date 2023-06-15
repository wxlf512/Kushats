package dev.wxlf.kushats.feature_bag.data.datasources.remote

import dev.wxlf.kushats.core.data.models.DishesModel


interface FoodRemoteDataSource {

    suspend fun loadDishes(): DishesModel
}

package dev.wxlf.kushats.feature_catalog.data.datasources.remote

import dev.wxlf.kushats.core.data.models.DishesModel
import dev.wxlf.kushats.core.data.retrofit.FoodApi

class FoodRetrofitDataSource(private val foodApi: FoodApi) : FoodRemoteDataSource {

    override suspend fun loadDishes(): DishesModel = foodApi.loadDishes()
}
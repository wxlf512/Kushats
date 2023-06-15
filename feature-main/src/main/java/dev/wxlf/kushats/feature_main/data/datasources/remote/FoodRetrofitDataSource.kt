package dev.wxlf.kushats.feature_main.data.datasources.remote

import dev.wxlf.kushats.core.data.models.CategoriesModel
import dev.wxlf.kushats.core.data.retrofit.FoodApi

class FoodRetrofitDataSource(private val foodApi: FoodApi) : FoodRemoteDataSource {

    override suspend fun loadCategories(): CategoriesModel = foodApi.loadCategories()
}
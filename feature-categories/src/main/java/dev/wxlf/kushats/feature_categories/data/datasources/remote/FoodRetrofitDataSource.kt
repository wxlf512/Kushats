package dev.wxlf.kushats.feature_categories.data.datasources.remote

import android.util.Log
import dev.wxlf.kushats.core.data.models.CategoriesModel
import dev.wxlf.kushats.core.data.retrofit.FoodApi

class FoodRetrofitDataSource(private val foodApi: FoodApi) : FoodRemoteDataSource {

    override suspend fun loadCategories(): CategoriesModel {
        val a = foodApi.loadCategories()
        Log.e("AAAAAAAAAAAAAAAAAAAA", a.toString())
        return a
    }
}
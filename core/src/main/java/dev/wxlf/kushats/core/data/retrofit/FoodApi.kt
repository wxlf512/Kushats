package dev.wxlf.kushats.core.data.retrofit

import dev.wxlf.kushats.core.data.models.CategoriesModel
import dev.wxlf.kushats.core.data.models.DishesModel
import retrofit2.http.GET

interface FoodApi {

    @GET("./058729bd-1402-4578-88de-265481fd7d54")
    suspend fun loadCategories(): CategoriesModel

    @GET("./aba7ecaa-0a70-453b-b62d-0e326c859b3b")
    suspend fun loadDishes(): DishesModel
}
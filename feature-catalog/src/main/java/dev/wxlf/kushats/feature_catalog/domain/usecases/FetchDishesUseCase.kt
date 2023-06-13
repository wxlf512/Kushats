package dev.wxlf.kushats.feature_catalog.domain.usecases

import dev.wxlf.kushats.core.data.models.DishesModel
import dev.wxlf.kushats.feature_catalog.domain.repositories.FoodRepository

class FetchDishesUseCase(private val foodRepository: FoodRepository) {

    sealed class Result {
        object Loading : Result()
        data class Success(val dishes: DishesModel) : Result()
        data class Failure(val msg: String) : Result()
    }

    suspend operator fun invoke(): Result =
        try {
            Result.Success(foodRepository.fetchDishes())
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage.orEmpty())
        }
}
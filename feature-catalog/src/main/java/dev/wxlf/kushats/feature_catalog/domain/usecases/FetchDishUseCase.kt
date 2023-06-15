package dev.wxlf.kushats.feature_catalog.domain.usecases

import dev.wxlf.kushats.core.data.models.DishModel
import dev.wxlf.kushats.feature_catalog.domain.repositories.FoodRepository

class FetchDishUseCase(private val foodRepository: FoodRepository) {

    sealed class Result {
        object Loading : Result()
        data class Success(val dish: DishModel) : Result()
        data class Failure(val msg: String) : Result()
        object NotFound : Result()
    }

    suspend operator fun invoke(id: Int): Result =
        try {
            val dish = foodRepository.fetchDishes().dishes.find { it.id == id }
            if (dish != null)
                Result.Success(dish)
            else
                Result.NotFound
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage.orEmpty())
        }
}
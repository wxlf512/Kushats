package dev.wxlf.kushats.feature_categories.domain.usecases

import dev.wxlf.kushats.core.data.models.CategoriesModel
import dev.wxlf.kushats.feature_categories.domain.FoodRepository
import java.lang.Exception

class FetchCategoriesUseCase(private val foodRepository: FoodRepository) {

    sealed class Result {
        object Loading : Result()
        data class Success(val categories: CategoriesModel) : Result()
        data class Failure(val msg: String) : Result()
    }

    suspend operator fun invoke(): Result =
        try {
            Result.Success(foodRepository.fetchCategories())
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage.orEmpty())
        }
}
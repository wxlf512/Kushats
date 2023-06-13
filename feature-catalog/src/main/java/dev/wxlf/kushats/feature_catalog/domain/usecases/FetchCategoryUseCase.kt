package dev.wxlf.kushats.feature_catalog.domain.usecases

import dev.wxlf.kushats.core.data.models.CategoryModel
import dev.wxlf.kushats.feature_catalog.domain.repositories.FoodRepository

class FetchCategoryUseCase(private val foodRepository: FoodRepository) {

    sealed class Result {
        object Loading : Result()
        data class Success(val category: CategoryModel) : Result()
        data class Failure(val msg: String) : Result()
        object NotFound : Result()
    }

    suspend operator fun invoke(id: Int): Result =
        try {
            val category = foodRepository.fetchCategories().categories.find { it.id == id }
            if (category != null)
                Result.Success(category)
            else
                Result.NotFound
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage.orEmpty())
        }
}
package dev.wxlf.kushats.feature_categories.domain.usecases

import dev.wxlf.kushats.core.data.models.CategoriesModel
import dev.wxlf.kushats.feature_categories.domain.FoodRepository
import java.lang.Exception

class FetchCategoriesUseCase(private val foodRepository: FoodRepository) {

    sealed class FetchCategoriesResult {
        object Loading : FetchCategoriesResult()
        data class Success(val categories: CategoriesModel) : FetchCategoriesResult()
        data class Failure(val msg: String) : FetchCategoriesResult()
    }

    suspend operator fun invoke(): FetchCategoriesResult =
        try {
            FetchCategoriesResult.Success(foodRepository.fetchCategories())
        } catch (e: Exception) {
            FetchCategoriesResult.Failure(e.localizedMessage.orEmpty())
        }
}
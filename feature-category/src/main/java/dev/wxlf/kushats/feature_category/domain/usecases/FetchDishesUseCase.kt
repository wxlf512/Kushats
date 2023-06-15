package dev.wxlf.kushats.feature_category.domain.usecases

import dev.wxlf.kushats.core.data.models.DishModel
import dev.wxlf.kushats.core.data.models.DishesModel
import dev.wxlf.kushats.feature_category.domain.repositories.FoodRepository
import dev.wxlf.kushats.feature_category.presentation.adapterdelegates.DishDisplayableItem

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

fun DishesModel.mapToDisplayable(): List<DishDisplayableItem> =
    this.dishes.map { it.mapToDisplayable() }

fun DishModel.mapToDisplayable(): DishDisplayableItem =
    DishDisplayableItem(id, name, imageUrl)
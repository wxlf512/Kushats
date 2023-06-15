package dev.wxlf.kushats.feature_bag.domain.usecases

import dev.wxlf.kushats.core.data.entities.ProductEntity
import dev.wxlf.kushats.core.data.models.DishesModel
import dev.wxlf.kushats.feature_bag.domain.repositories.BagRepository
import dev.wxlf.kushats.feature_bag.domain.repositories.FoodRepository

class FetchDishesUseCase(
    private val foodRepository: FoodRepository,
    private val bagRepository: BagRepository
) {

    sealed class Result {
        object Loading : Result()
        data class Success(val dishes: DishesModel, val products: List<ProductEntity>) : Result()
        data class Failure(val msg: String) : Result()
    }

    suspend operator fun invoke(): Result =
        try {
            val bag = bagRepository.fetchProducts()
            val bagIds = bag.map { it.id }
            val dishes = DishesModel(foodRepository.fetchDishes().dishes.filter { it.id in bagIds })
            Result.Success(dishes, bag)
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage.orEmpty())
        }
}

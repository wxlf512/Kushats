package dev.wxlf.kushats.feature_bag.domain.usecases

import dev.wxlf.kushats.core.data.entities.ProductEntity
import dev.wxlf.kushats.core.data.models.DishModel
import dev.wxlf.kushats.core.data.models.DishesModel
import dev.wxlf.kushats.feature_bag.domain.repositories.BagRepository
import dev.wxlf.kushats.feature_bag.domain.repositories.FoodRepository
import dev.wxlf.kushats.feature_bag.presentation.adapterdelegates.ProductDisplayableItem

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
            val bag = bagRepository.fetchProducts().sortedBy { it.id }
            val bagIds = bag.map { it.id }
            val dishes = DishesModel(foodRepository.fetchDishes().dishes.filter { it.id in bagIds }
                .sortedBy { it.id })
            Result.Success(dishes, bag)
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage.orEmpty())
        }
}

fun DishesModel.mapToDisplayable(products: List<ProductEntity>): List<ProductDisplayableItem> =
    dishes.zip(products) { dish, product ->
        dish.mapToDisplayable(product)
    }

fun DishModel.mapToDisplayable(product: ProductEntity): ProductDisplayableItem =
    ProductDisplayableItem(id, name, imageUrl, price, weight, product.count)
package dev.wxlf.kushats.feature_category.domain.usecases

import dev.wxlf.kushats.core.data.entities.ProductEntity
import dev.wxlf.kushats.core.data.models.DishModel
import dev.wxlf.kushats.feature_category.domain.repositories.BagRepository

class AddProductToBagUseCase(private val bagRepository: BagRepository) {

    sealed class Result {
        object Loading : Result()
        object Success : Result()
        data class Failure(val msg: String) : Result()
    }

    suspend operator fun invoke(dishModel: DishModel): Result =
        try {
            bagRepository.addProduct(ProductEntity(dishModel.id, 1))
            Result.Success
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage.orEmpty())
        }
}
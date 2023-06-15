package dev.wxlf.kushats.feature_bag.domain.usecases

import dev.wxlf.kushats.core.data.entities.ProductEntity
import dev.wxlf.kushats.feature_bag.domain.repositories.BagRepository

class IncrementProductCountUseCase(private val bagRepository: BagRepository) {

    sealed class Result {
        object Loading : Result()
        object Success : Result()
        data class Failure(val msg: String) : Result()
    }

    suspend operator fun invoke(productEntity: ProductEntity): Result =
        try {
            bagRepository.updateProduct(productEntity.copy(count = productEntity.count + 1))
            Result.Success
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage.orEmpty())
        }
}
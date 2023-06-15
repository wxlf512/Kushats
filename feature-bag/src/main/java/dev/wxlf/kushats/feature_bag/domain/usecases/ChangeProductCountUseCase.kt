package dev.wxlf.kushats.feature_bag.domain.usecases

import dev.wxlf.kushats.core.data.entities.ProductEntity
import dev.wxlf.kushats.feature_bag.common.ProductCount
import dev.wxlf.kushats.feature_bag.common.ProductCount.*
import dev.wxlf.kushats.feature_bag.domain.repositories.BagRepository

class ChangeProductCountUseCase(private val bagRepository: BagRepository) {

    sealed class Result {
        object Loading : Result()
        object Success : Result()
        data class Failure(val msg: String) : Result()
    }

    suspend operator fun invoke(productEntity: ProductEntity, action: ProductCount): Result =
        try {
            when (action) {
                INCREMENT -> {
                    bagRepository.updateProduct(productEntity.copy(count = productEntity.count + 1))
                }
                DECREMENT -> {
                    if (productEntity.count > 1)
                        bagRepository.updateProduct(productEntity.copy(count = productEntity.count - 1))
                    else
                        bagRepository.deleteProduct(productEntity)
                }
            }
            Result.Success
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage.orEmpty())
        }
}
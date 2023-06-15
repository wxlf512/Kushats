package dev.wxlf.kushats.feature_bag.domain.repositories

import dev.wxlf.kushats.core.data.entities.ProductEntity

interface BagRepository {

    suspend fun fetchProducts(): List<ProductEntity>
    suspend fun updateProduct(productEntity: ProductEntity)
    suspend fun deleteProduct(productEntity: ProductEntity)

}
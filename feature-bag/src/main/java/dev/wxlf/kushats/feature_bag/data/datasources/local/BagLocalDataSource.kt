package dev.wxlf.kushats.feature_bag.data.datasources.local

import dev.wxlf.kushats.core.data.entities.ProductEntity


interface BagLocalDataSource {

    suspend fun loadProducts(): List<ProductEntity>
    suspend fun updateProduct(productEntity: ProductEntity)
    suspend fun deleteProduct(productEntity: ProductEntity)

}

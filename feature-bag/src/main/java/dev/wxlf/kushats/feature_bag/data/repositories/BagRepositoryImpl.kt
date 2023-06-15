package dev.wxlf.kushats.feature_bag.data.repositories

import dev.wxlf.kushats.core.data.entities.ProductEntity
import dev.wxlf.kushats.feature_bag.data.datasources.local.BagLocalDataSource
import dev.wxlf.kushats.feature_bag.domain.repositories.BagRepository

class BagRepositoryImpl(private val localDataSource: BagLocalDataSource) : BagRepository {
    override suspend fun fetchProducts(): List<ProductEntity> = localDataSource.loadProducts()

    override suspend fun updateProduct(productEntity: ProductEntity) =
        localDataSource.updateProduct(productEntity)

    override suspend fun deleteProduct(productEntity: ProductEntity) =
        localDataSource.deleteProduct(productEntity)
}
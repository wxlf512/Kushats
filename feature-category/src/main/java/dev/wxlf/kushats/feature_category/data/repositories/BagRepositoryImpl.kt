package dev.wxlf.kushats.feature_category.data.repositories

import dev.wxlf.kushats.core.data.entities.ProductEntity
import dev.wxlf.kushats.feature_category.data.datasources.local.BagLocalDataSource
import dev.wxlf.kushats.feature_category.domain.repositories.BagRepository

class BagRepositoryImpl(private val localDataSource: BagLocalDataSource) : BagRepository {

    override suspend fun addProduct(productEntity: ProductEntity) = localDataSource.insertProduct(productEntity)
}
package dev.wxlf.kushats.feature_catalog.data.repositories

import dev.wxlf.kushats.core.data.entities.ProductEntity
import dev.wxlf.kushats.feature_catalog.data.datasources.local.BagLocalDataSource
import dev.wxlf.kushats.feature_catalog.domain.repositories.BagRepository

class BagRepositoryImpl(private val localDataSource: BagLocalDataSource) : BagRepository {

    override suspend fun addProduct(productEntity: ProductEntity) = localDataSource.insertProduct(productEntity)
}
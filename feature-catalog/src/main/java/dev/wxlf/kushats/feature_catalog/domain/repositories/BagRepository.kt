package dev.wxlf.kushats.feature_catalog.domain.repositories

import dev.wxlf.kushats.core.data.entities.ProductEntity

interface BagRepository {

    suspend fun addProduct(productEntity: ProductEntity)
}
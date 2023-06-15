package dev.wxlf.kushats.feature_category.domain.repositories

import dev.wxlf.kushats.core.data.entities.ProductEntity

interface BagRepository {

    suspend fun addProduct(productEntity: ProductEntity)
}
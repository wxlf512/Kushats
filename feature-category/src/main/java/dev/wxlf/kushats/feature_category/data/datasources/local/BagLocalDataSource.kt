package dev.wxlf.kushats.feature_category.data.datasources.local

import dev.wxlf.kushats.core.data.entities.ProductEntity


interface BagLocalDataSource {

    suspend fun insertProduct(productEntity: ProductEntity)

}

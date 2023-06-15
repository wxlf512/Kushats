package dev.wxlf.kushats.feature_bag.data.datasources.local

import dev.wxlf.kushats.core.data.entities.ProductEntity
import dev.wxlf.kushats.core.data.room.BagDao

class BagRoomDataSource(private val bagDao: BagDao) : BagLocalDataSource {
    override suspend fun loadProducts(): List<ProductEntity> = bagDao.loadBag()

    override suspend fun updateProduct(productEntity: ProductEntity) =
        bagDao.updateProduct(productEntity)

    override suspend fun deleteProduct(productEntity: ProductEntity) =
        bagDao.deleteProduct(productEntity)
}
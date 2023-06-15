package dev.wxlf.kushats.feature_category.data.datasources.local

import dev.wxlf.kushats.core.data.entities.ProductEntity
import dev.wxlf.kushats.core.data.room.BagDao

class BagRoomDataSource(private val bagDao: BagDao) : BagLocalDataSource {

    override suspend fun insertProduct(productEntity: ProductEntity) =
        bagDao.insertProduct(productEntity)
}
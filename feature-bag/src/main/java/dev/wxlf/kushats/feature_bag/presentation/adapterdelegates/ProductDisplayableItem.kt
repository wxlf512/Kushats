package dev.wxlf.kushats.feature_bag.presentation.adapterdelegates

import dev.wxlf.kushats.core.data.entities.ProductEntity

data class ProductDisplayableItem(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val price: Int,
    val weight: Int,
    val count: Int
) : DisplayableItem {
    fun mapToProductEntity(): ProductEntity = ProductEntity(id, count)
}
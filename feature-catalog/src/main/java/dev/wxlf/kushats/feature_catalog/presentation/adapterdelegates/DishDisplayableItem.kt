package dev.wxlf.kushats.feature_catalog.presentation.adapterdelegates

data class DishDisplayableItem(
    var id: Int,
    var name: String,
    var imageUrl: String
) : DisplayableItem
package dev.wxlf.kushats.feature_category.presentation.adapterdelegates

data class DishDisplayableItem(
    var id: Int,
    var name: String,
    var imageUrl: String
) : DisplayableItem
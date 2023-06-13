package dev.wxlf.kushats.feature_categories.presentation.adapterdelegates

data class CategoryDisplayableItem(
    var id: Int,
    var name: String,
    var imageUrl: String
) : DisplayableItem

package dev.wxlf.kushats.core.data.models

import com.google.gson.annotations.SerializedName

data class CategoriesModel(
    @SerializedName("сategories") var categories: List<CategoryModel>
)

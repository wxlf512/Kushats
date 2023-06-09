package dev.wxlf.kushats.core.data.models

import com.google.gson.annotations.SerializedName

data class CategoriesModel(
    @SerializedName("categories") var categories: List<CategoryModel>
)

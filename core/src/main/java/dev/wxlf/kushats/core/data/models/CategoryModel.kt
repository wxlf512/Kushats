package dev.wxlf.kushats.core.data.models

import com.google.gson.annotations.SerializedName

data class CategoryModel(
    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String,
    @SerializedName("image_url") var imageUrl: String
)
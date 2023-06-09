package dev.wxlf.kushats.core.data.models

import com.google.gson.annotations.SerializedName

data class DishModel(
    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String,
    @SerializedName("price") var price: Int,
    @SerializedName("weight") var weight: Int,
    @SerializedName("description") var description: String,
    @SerializedName("image_url") var imageUrl: String,
    @SerializedName("tegs") var tags: List<String>
)

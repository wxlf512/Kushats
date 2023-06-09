package dev.wxlf.kushats.core.data.models

import com.google.gson.annotations.SerializedName

data class DishesModel(
    @SerializedName("dishes") var dishes: List<DishModel>
)

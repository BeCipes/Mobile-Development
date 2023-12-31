package com.development.gocipes.core.data.remote.response.food

import com.google.gson.annotations.SerializedName

data class FoodResponse(

    @field:SerializedName("msg")
    val msg: String? = null,

    @field:SerializedName("code")
    val code: Int? = null,

    @field:SerializedName("data")
    val data: List<FoodItem>? = null,

    @field:SerializedName("status")
    val status: String? = null,
)
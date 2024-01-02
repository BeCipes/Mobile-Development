package com.development.gocipes.core.data.remote.response.detail

import com.development.gocipes.core.data.remote.response.food.FoodItem
import com.google.gson.annotations.SerializedName

data class DetailRecipeResponse(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: FoodItem? = null,

	@field:SerializedName("status")
	val status: String? = null
)
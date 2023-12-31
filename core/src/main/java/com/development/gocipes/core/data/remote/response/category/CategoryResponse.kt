package com.development.gocipes.core.data.remote.response.category

import com.google.gson.annotations.SerializedName

data class CategoryResponse(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: List<CategoryItem>? = null,

	@field:SerializedName("status")
	val status: String? = null,
)
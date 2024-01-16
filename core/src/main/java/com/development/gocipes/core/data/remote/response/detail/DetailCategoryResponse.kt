package com.development.gocipes.core.data.remote.response.detail

import com.development.gocipes.core.data.remote.response.category.CategoryItem
import com.google.gson.annotations.SerializedName

data class DetailCategoryResponse(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: List<CategoryItem>,

	@field:SerializedName("status")
	val status: String? = null,
)
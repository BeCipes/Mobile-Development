package com.development.gocipes.core.data.remote.response.favorite

import com.google.gson.annotations.SerializedName

data class DeleteFavoriteResponse(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("status")
	val status: String? = null
)

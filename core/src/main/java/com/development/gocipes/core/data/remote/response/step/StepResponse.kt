package com.development.gocipes.core.data.remote.response.step

import com.google.gson.annotations.SerializedName

data class StepResponse(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: List<StepItem>? = null,

	@field:SerializedName("status")
	val status: String? = null
)
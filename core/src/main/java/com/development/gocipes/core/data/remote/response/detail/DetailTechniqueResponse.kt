package com.development.gocipes.core.data.remote.response.detail

import com.development.gocipes.core.data.remote.response.technique.TechniqueItem
import com.google.gson.annotations.SerializedName

data class DetailTechniqueResponse(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: TechniqueItem? = null,

	@field:SerializedName("status")
	val status: String? = null
)
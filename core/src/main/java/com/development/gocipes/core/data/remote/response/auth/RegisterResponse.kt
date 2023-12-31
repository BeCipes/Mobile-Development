package com.development.gocipes.core.data.remote.response.auth

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

    @field:SerializedName("msg")
	val msg: String? = null,

    @field:SerializedName("code")
	val code: Int? = null,

    @field:SerializedName("data")
	val data: RegisterItem? = null,

    @field:SerializedName("status")
	val status: String? = null
)

data class RegisterItem(

	@field:SerializedName("token")
	val token: Token? = null
)

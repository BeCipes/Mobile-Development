package com.development.gocipes.core.data.remote.response.auth

import com.google.gson.annotations.SerializedName

data class GetUserResponse(

    @field:SerializedName("msg")
	val msg: String,

    @field:SerializedName("code")
	val code: Int,

    @field:SerializedName("data")
	val data: UserResult,

    @field:SerializedName("status")
	val status: String
)

data class UserResult(

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("role")
	val role: String,

	@field:SerializedName("isVerified")
	val isVerified: Boolean,

	@field:SerializedName("last_name")
	val lastName: String,

	@field:SerializedName("photo")
	val photo: String?= null,

	@field:SerializedName("first_name")
	val firstName: String,

	@field:SerializedName("email")
	val email: String
)

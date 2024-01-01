package com.development.gocipes.core.data.remote.response.detail

import com.development.gocipes.core.data.remote.response.article.ArticleItem
import com.google.gson.annotations.SerializedName

data class DetailArticleResponse(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: ArticleItem? = null,

	@field:SerializedName("status")
	val status: String? = null
)
package com.development.gocipes.core.data.remote.response.article

import com.google.gson.annotations.SerializedName

data class ArticleResponse(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: List<ArticleItem>,

	@field:SerializedName("status")
	val status: String? = null
)

data class ArticleItem(

	@field:SerializedName("penulis")
	val penulis: String? = null,

	@field:SerializedName("id_kategori")
	val idKategori: Int? = null,

	@field:SerializedName("sumber")
	val sumber: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("headline")
	val headline: String? = null,

	@field:SerializedName("gambar")
	val gambar: List<String?>? = null,

	@field:SerializedName("isi")
	val isi: String? = null
)

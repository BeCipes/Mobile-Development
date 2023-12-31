package com.development.gocipes.core.data.remote.response.article

import com.google.gson.annotations.SerializedName

data class DetailArticleResponse(

	@field:SerializedName("msg")
	val msg: String,

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("data")
	val data: List<DetailArticleItem>,

	@field:SerializedName("status")
	val status: String
)

data class DetailArticleItem(

	@field:SerializedName("penulis")
	val penulis: String,

	@field:SerializedName("id_kategori")
	val idKategori: Int,

	@field:SerializedName("sumber")
	val sumber: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("headline")
	val headline: String,

	@field:SerializedName("gambar")
	val gambar: List<String>,

	@field:SerializedName("isi")
	val isi: String
)

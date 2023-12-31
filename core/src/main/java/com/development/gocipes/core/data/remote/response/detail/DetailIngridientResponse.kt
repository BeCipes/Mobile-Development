package com.development.gocipes.core.data.remote.response.detail

import com.development.gocipes.core.data.remote.response.analysis.Gizi
import com.google.gson.annotations.SerializedName

data class DetailIngridientResponse(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: DetailIngridientItems? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DetailIngridientItems(

	@field:SerializedName("gizi")
	val gizi: Gizi? = null,

	@field:SerializedName("nama_bahan")
	val namaBahan: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("deskripsi")
	val deskripsi: String? = null,

	@field:SerializedName("gambar")
	val gambar: String? = null
)

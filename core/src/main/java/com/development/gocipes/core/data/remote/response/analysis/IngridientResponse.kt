package com.development.gocipes.core.data.remote.response.analysis

import com.google.gson.annotations.SerializedName

data class IngridientResponse(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: List<IngridientItem>? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Gizi(

	@field:SerializedName("karbohidrat")
	val karbohidrat: String? = null,

	@field:SerializedName("protein")
	val protein: String? = null,

	@field:SerializedName("lemak")
	val lemak: String? = null
)

data class IngridientItem(

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

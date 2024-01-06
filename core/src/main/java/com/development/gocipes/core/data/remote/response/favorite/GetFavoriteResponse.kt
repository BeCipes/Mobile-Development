package com.development.gocipes.core.data.remote.response.favorite

import com.google.gson.annotations.SerializedName

data class GetFavoriteResponse(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: List<GetFavoriteItem>? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class InformasiGizi(

	@field:SerializedName("karbohidrat")
	val karbohidrat: String? = null,

	@field:SerializedName("protein")
	val protein: String? = null,

	@field:SerializedName("lemak")
	val lemak: String? = null
)

data class Resep(

	@field:SerializedName("bahan")
	val bahan: List<String?>? = null,

	@field:SerializedName("informasi_gizi")
	val informasiGizi: InformasiGizi? = null,

	@field:SerializedName("nama_resep")
	val namaResep: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("deskripsi")
	val deskripsi: String? = null,

	@field:SerializedName("gambar")
	val gambar: String? = null
)

data class GetFavoriteItem(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("resep")
	val resep: Resep? = null
)

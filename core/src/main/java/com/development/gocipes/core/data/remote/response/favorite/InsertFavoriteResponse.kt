package com.development.gocipes.core.data.remote.response.favorite

import com.google.gson.annotations.SerializedName

data class InsertFavoriteResponse(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: InsertFavoriteItem? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class InsertFavoriteItem(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("user")
	val user: User? = null,

	@field:SerializedName("resep")
	val resep: InsertResep? = null
)

data class InsertResep(

	@field:SerializedName("bahan")
	val bahan: List<String?>? = null,

	@field:SerializedName("informasi_gizi")
	val informasiGizi: InsertInformasiGizi? = null,

	@field:SerializedName("nama_resep")
	val namaResep: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("deskripsi")
	val deskripsi: String? = null,

	@field:SerializedName("gambar")
	val gambar: String? = null
)

data class User(

	@field:SerializedName("last_name")
	val lastName: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("first_name")
	val firstName: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)

data class InsertInformasiGizi(

	@field:SerializedName("karbohidrat")
	val karbohidrat: String? = null,

	@field:SerializedName("protein")
	val protein: String? = null,

	@field:SerializedName("lemak")
	val lemak: String? = null
)

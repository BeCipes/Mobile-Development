package com.development.gocipes.core.data.remote.response.food

import com.google.gson.annotations.SerializedName

data class FoodItem(

    @field:SerializedName("bahan")
    val bahan: List<String>? = null,

    @field:SerializedName("informasi_gizi")
    val informasiGizi: NutritionItem? = null,

    @field:SerializedName("nama_resep")
    val namaResep: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("deskripsi")
    val deskripsi: String? = null,

    @field:SerializedName("gambar")
    val gambar: String? = null,
)

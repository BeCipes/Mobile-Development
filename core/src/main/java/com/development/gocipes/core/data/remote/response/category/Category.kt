package com.development.gocipes.core.data.remote.response.category

import com.google.gson.annotations.SerializedName

data class Category(

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("gambar")
    val gambar: String? = null,

    @field:SerializedName("nama_kategori")
    val namaKategori: String? = null
)
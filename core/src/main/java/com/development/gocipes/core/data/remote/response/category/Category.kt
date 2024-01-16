package com.development.gocipes.core.data.remote.response.category

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("gambar")
    val gambar: String? = null,

    @field:SerializedName("nama_kategori")
    val namaKategori: String? = null
): Parcelable
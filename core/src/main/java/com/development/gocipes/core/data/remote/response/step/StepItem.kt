package com.development.gocipes.core.data.remote.response.step

import com.google.gson.annotations.SerializedName

data class StepItem(

    @field:SerializedName("step_desc")
    val stepDesc: String? = null,

    @field:SerializedName("step_no")
    val stepNo: Int? = null,

    @field:SerializedName("waktu")
    val waktu: Int? = null,

    @field:SerializedName("id_resep")
    val idResep: Int? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("gambar")
    val gambar: String? = null
)
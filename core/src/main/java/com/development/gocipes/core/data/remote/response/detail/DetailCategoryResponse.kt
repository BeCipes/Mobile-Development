package com.development.gocipes.core.data.remote.response.detail

import com.development.gocipes.core.data.remote.response.category.Category
import com.development.gocipes.core.data.remote.response.food.FoodItem
import com.google.gson.annotations.SerializedName

data class DetailCategoryResponse(

    @field:SerializedName("msg")
    val msg: String? = null,

    @field:SerializedName("code")
    val code: Int? = null,

    @field:SerializedName("data")
    val data: DetailCategoryItem? = null,

    @field:SerializedName("status")
    val status: String? = null,
)

data class DetailCategoryItem(

    @field:SerializedName("kategori")
    val kategori: Category? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("resep")
    val resep: FoodItem? = null,
)

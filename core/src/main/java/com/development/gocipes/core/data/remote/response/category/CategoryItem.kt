package com.development.gocipes.core.data.remote.response.category

import com.development.gocipes.core.data.remote.response.food.FoodItem
import com.google.gson.annotations.SerializedName

data class CategoryItem(

    @field:SerializedName("kategori")
    val kategori: Category,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("resep")
    val resep: FoodItem? = null,
)
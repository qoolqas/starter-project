package com.example.starterproject.data

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("discount")
    val discount: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("images")
    val images: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("price")
    val price: Double?,
    @SerializedName("rating")
    val rating: Double?,
    @SerializedName("sort_description")
    val sortDescription: String?
)
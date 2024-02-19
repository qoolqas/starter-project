package com.example.starterproject.data

import com.example.starterproject.domain.ProductDomain
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
){
    fun toDomain(): ProductDomain{
        return ProductDomain(
            discount = discount ?: 0,
            id = id ?: 0,
            images = images.orEmpty(),
            name = name.orEmpty(),
            price = price ?: 0.0,
            rating = rating ?: 0.0,
            sortDescription = sortDescription.orEmpty(),
        )
    }
}
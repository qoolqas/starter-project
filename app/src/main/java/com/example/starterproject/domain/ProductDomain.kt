package com.example.starterproject.domain

import com.google.gson.annotations.SerializedName

data class ProductDomain (

    val discount: Int,
    val id: Int,
    val images: String,
    val name: String,
    val price: Double,
    val rating: Double,
    val sortDescription: String
)
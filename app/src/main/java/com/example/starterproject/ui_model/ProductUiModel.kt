package com.example.starterproject.ui_model

import com.example.starterproject.domain.ProductDomain

data class ProductUiModel (

    val discount: Int,
    val id: Int,
    val images: String,
    val name: String,
    val price: Double,
    val rating: Double,
    val sortDescription: String,

    var totalCart : Int = 0
){
    companion object{
        fun parse(domain: ProductDomain?): ProductUiModel{
            return  ProductUiModel(
                discount = domain?.discount ?: 0,
                id = domain?.id ?: 0,
                images = domain?.images.orEmpty(),
                name = domain?.name.orEmpty(),
                price = domain?.price ?: 0.0,
                rating = domain?.rating ?: 0.0,
                sortDescription = domain?.sortDescription.orEmpty(),
            )
        }
    }
}
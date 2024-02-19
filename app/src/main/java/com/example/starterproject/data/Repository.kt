package com.example.starterproject.data

import com.example.starterproject.domain.ProductDomain
import com.example.starterproject.utils.api_handler.ApiHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface Repository {
    suspend fun getProducts() :List<ProductDomain>?
}

class RepositoryImpl(
    private val apiService: ApiService
) : Repository {
    override suspend fun getProducts(): List<ProductDomain>? {
        val result = withContext(Dispatchers.IO){
            ApiHandler.handleApi {
                apiService.getProducts()
            }
        }
        return result?.map {
            it.toDomain()
        }
    }
}
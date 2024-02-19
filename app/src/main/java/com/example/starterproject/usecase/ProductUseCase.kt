package com.example.starterproject.usecase

import com.example.starterproject.data.Repository
import com.example.starterproject.domain.ProductDomain
import com.example.starterproject.utils.interactor.ResultState
import com.example.starterproject.utils.interactor.SuspendUseCase
import javax.inject.Inject

class ProductUseCase  @Inject constructor(
    private val repository: Repository
) : SuspendUseCase<ProductUseCase.RequestValues, ProductUseCase.ResponseValues>(){
    class RequestValues() : SuspendUseCase.RequestValues

    class ResponseValues(val result: ResultState<List<ProductDomain>?>) : SuspendUseCase.ResponseValues

    override suspend fun execute(requestValues: RequestValues): ResponseValues {
        return try {
            val result = repository.getProducts()
            ResponseValues(ResultState.Success(result))
        }catch (e : Exception){
            ResponseValues(ResultState.Error(e))
        }
    }
}
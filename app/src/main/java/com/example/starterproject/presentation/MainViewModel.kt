package com.example.starterproject.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starterproject.data.Repository
import com.example.starterproject.ui_model.ProductUiModel
import com.example.starterproject.usecase.ProductUseCase
import com.example.starterproject.utils.interactor.ResultState
import com.example.starterproject.utils.interactor.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val productUseCase: ProductUseCase,
    private val repository : Repository
) : ViewModel() {

    private val _loading by lazy { MutableSharedFlow<Boolean>() }
    val loading: SharedFlow<Boolean> = _loading

    private val _productResult by lazy {
        MutableSharedFlow<ResultState<List<ProductUiModel>?>>()
    }
    val productResult = _productResult.asSharedFlow()

    private val _cartResult by lazy {
        MutableSharedFlow<ResultState<List<ProductUiModel>?>>()
    }
    val cartResult = _productResult.asSharedFlow()

    fun getProducts(){
        viewModelScope.launch {
            _loading.emit(true)
            productUseCase.execute(
                ProductUseCase.RequestValues()
            ).result
                .map {
                    it?.map {domain ->
                        ProductUiModel.parse(domain)
                    }
                }
                .let {
                    _loading.emit(false)
                    _productResult.emit(it)
                }
        }
    }
    fun minusCart(product: ProductUiModel) {
        product.totalCart++
        emitUpdatedProductResult()
    }
    fun plusCart(product: ProductUiModel) {
        product.totalCart++
        emitUpdatedProductResult()
    }
    private fun emitUpdatedProductResult() {
        viewModelScope.launch {
            val productUiModels = _productResult.replayCache.first { it != null }
            _productResult.emit(productUiModels)
        }
    }
}
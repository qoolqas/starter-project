package com.example.starterproject.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starterproject.data.Repository
import com.example.starterproject.ui_model.ProductUiModel
import com.example.starterproject.usecase.ProductUseCase
import com.example.starterproject.utils.interactor.ResultState
import com.example.starterproject.utils.interactor.getDataOrNull
import com.example.starterproject.utils.interactor.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val productUseCase: ProductUseCase
) : ViewModel() {

    private val _loading by lazy { MutableSharedFlow<Boolean>() }
    val loading: SharedFlow<Boolean> = _loading

    private val _productResult by lazy {
        MutableSharedFlow<ResultState<List<ProductUiModel>?>>()
    }
    val productResult = _productResult.asSharedFlow()

    private val productUiModelsState = _productResult
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

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

        val currentProductUiModels = productUiModelsState.value?.getDataOrNull() ?: emptyList()
        val updatedProductUiModels = currentProductUiModels.map {
            if (it.id == product.id) {
                if (it.totalCart == 0) it.copy(totalCart = it.totalCart)
                else  it.copy(totalCart = it.totalCart - 1)
            } else {
                it
            }
        }
        viewModelScope.launch {
            _productResult.emit(ResultState.Success(updatedProductUiModels))
        }
    }
    fun plusCart(product: ProductUiModel) {
        val currentProductUiModels = productUiModelsState.value?.getDataOrNull() ?: emptyList()
        val updatedProductUiModels = currentProductUiModels.map {
            if (it.id == product.id) {
                it.copy(totalCart = it.totalCart + 1)
            } else {
                it
            }
        }
        viewModelScope.launch {
            _productResult.emit(ResultState.Success(updatedProductUiModels))
        }
    }
}
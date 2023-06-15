package dev.wxlf.kushats.feature_catalog.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.wxlf.kushats.core.data.models.DishModel
import dev.wxlf.kushats.feature_catalog.domain.usecases.AddProductToBagUseCase
import dev.wxlf.kushats.feature_catalog.domain.usecases.FetchDishUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductDialogViewModel @Inject constructor(
    private val fetchDishUseCase: FetchDishUseCase,
    private val addProductToBagUseCase: AddProductToBagUseCase
) : ViewModel() {

    private val _fetchDishState =
        MutableStateFlow<FetchDishUseCase.Result>(FetchDishUseCase.Result.Loading)
    val fetchDishState = _fetchDishState.asStateFlow()

    private val _addProductToBagState =
        MutableStateFlow<AddProductToBagUseCase.Result>(AddProductToBagUseCase.Result.Loading)
    val addProductToBagState = _addProductToBagState.asStateFlow()

    fun fetchDish(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _fetchDishState.emit(FetchDishUseCase.Result.Loading)
            _fetchDishState.emit(fetchDishUseCase(id))
        }
    }

    fun addDishToBag(dishModel: DishModel) {
        viewModelScope.launch(Dispatchers.IO) {
            _addProductToBagState.emit(AddProductToBagUseCase.Result.Loading)
            _addProductToBagState.emit(addProductToBagUseCase(dishModel))
        }
    }
}
package dev.wxlf.kushats.feature_bag.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.wxlf.kushats.feature_bag.common.ProductCount
import dev.wxlf.kushats.feature_bag.domain.usecases.ChangeProductCountUseCase
import dev.wxlf.kushats.feature_bag.domain.usecases.FetchDishesUseCase
import dev.wxlf.kushats.feature_bag.presentation.adapterdelegates.ProductDisplayableItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class BagViewModel @Inject constructor(
    private val fetchDishesUseCase: FetchDishesUseCase,
    private val changeProductCountUseCase: ChangeProductCountUseCase
) : ViewModel() {

    private val _fetchDishesState =
        MutableStateFlow<FetchDishesUseCase.Result>(FetchDishesUseCase.Result.Loading)
    val fetchDishesState = _fetchDishesState.asStateFlow()

    private val _changeProductCountState =
        MutableStateFlow<ChangeProductCountUseCase.Result>(ChangeProductCountUseCase.Result.Loading)
    val changeProductCountState = _changeProductCountState.asStateFlow()

    fun fetchDishes() =
        viewModelScope.launch(Dispatchers.IO) {
            _fetchDishesState.emit(FetchDishesUseCase.Result.Loading)
            _fetchDishesState.emit(fetchDishesUseCase())
        }

    fun changeProductCount(productItem: ProductDisplayableItem, action: ProductCount) =
        viewModelScope.launch(Dispatchers.IO) {
            _changeProductCountState.emit(ChangeProductCountUseCase.Result.Loading)
            _changeProductCountState.emit(changeProductCountUseCase(productEntity = productItem.mapToProductEntity(), action))
        }
}
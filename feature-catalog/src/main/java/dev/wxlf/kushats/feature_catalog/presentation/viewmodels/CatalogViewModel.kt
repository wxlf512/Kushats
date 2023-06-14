package dev.wxlf.kushats.feature_catalog.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.wxlf.kushats.core.data.models.DishModel
import dev.wxlf.kushats.core.data.models.DishesModel
import dev.wxlf.kushats.feature_catalog.domain.usecases.AddProductToBagUseCase
import dev.wxlf.kushats.feature_catalog.domain.usecases.FetchCategoryUseCase
import dev.wxlf.kushats.feature_catalog.domain.usecases.FetchDishesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CatalogViewModel @Inject constructor(
    private val fetchCategoryUseCase: FetchCategoryUseCase,
    private val fetchDishesUseCase: FetchDishesUseCase,
    private val addProductToBagUseCase: AddProductToBagUseCase
) : ViewModel() {

    private val _fetchCategoryState =
        MutableStateFlow<FetchCategoryUseCase.Result>(FetchCategoryUseCase.Result.Loading)
    val fetchCategoryState = _fetchCategoryState.asStateFlow()

    private val _fetchDishesState =
        MutableStateFlow<FetchDishesUseCase.Result>(FetchDishesUseCase.Result.Loading)
    val fetchDishesState = _fetchDishesState.asStateFlow()

    private val _addProductToBagState =
        MutableStateFlow<AddProductToBagUseCase.Result>(AddProductToBagUseCase.Result.Loading)
    val addProductToBagState = _addProductToBagState.asStateFlow()

    fun fetchCategory(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _fetchCategoryState.emit(FetchCategoryUseCase.Result.Loading)
            _fetchCategoryState.emit(fetchCategoryUseCase(id))
        }
    }

    fun fetchDishes(tag: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _fetchDishesState.emit(FetchDishesUseCase.Result.Loading)
            val result = fetchDishesUseCase()
            if (result is FetchDishesUseCase.Result.Success)
                _fetchDishesState.emit(
                    FetchDishesUseCase.Result.Success(
                        DishesModel(result.dishes.dishes.filter {
                            it.tags.contains(tag)
                        })
                    )
                )
            else
                _fetchDishesState.emit(result)
        }
    }

    fun addDishToBag(dishModel: DishModel) {
        viewModelScope.launch(Dispatchers.IO) {
            _addProductToBagState.emit(AddProductToBagUseCase.Result.Loading)
            _addProductToBagState.emit(addProductToBagUseCase(dishModel))
        }
    }
}
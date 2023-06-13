package dev.wxlf.kushats.feature_categories.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.wxlf.kushats.feature_categories.domain.usecases.FetchCategoriesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CategoriesViewModel @Inject constructor(
    private val fetchCategoriesUseCase: FetchCategoriesUseCase
) : ViewModel() {

    private val _fetchCategoriesState =
        MutableStateFlow<FetchCategoriesUseCase.Result>(FetchCategoriesUseCase.Result.Loading)
    val fetchCategoriesState = _fetchCategoriesState.asStateFlow()

    fun fetchCategories() =
        viewModelScope.launch(Dispatchers.IO) {
            _fetchCategoriesState.emit(FetchCategoriesUseCase.Result.Loading)
            _fetchCategoriesState.emit(fetchCategoriesUseCase())
        }
}
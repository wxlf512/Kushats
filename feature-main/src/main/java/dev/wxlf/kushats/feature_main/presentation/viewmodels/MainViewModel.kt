package dev.wxlf.kushats.feature_main.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.wxlf.kushats.feature_main.domain.usecases.FetchCategoriesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
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
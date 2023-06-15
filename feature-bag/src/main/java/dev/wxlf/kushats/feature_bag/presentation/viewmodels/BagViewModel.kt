package dev.wxlf.kushats.feature_bag.presentation.viewmodels

import androidx.lifecycle.ViewModel
import dev.wxlf.kushats.feature_bag.domain.usecases.DecrementProductCountUseCase
import dev.wxlf.kushats.feature_bag.domain.usecases.FetchDishesUseCase
import dev.wxlf.kushats.feature_bag.domain.usecases.IncrementProductCountUseCase
import javax.inject.Inject

class BagViewModel @Inject constructor(
    private val fetchDishesUseCase: FetchDishesUseCase,
    private val incrementProductCountUseCase: IncrementProductCountUseCase,
    private val decrementProductCountUseCase: DecrementProductCountUseCase
) : ViewModel() {

}
package dev.wxlf.kushats.di.viewmodel

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dev.wxlf.kushats.feature_categories.presentation.viewmodels.CategoriesViewModel

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CategoriesViewModel::class)
    abstract fun bindsCategoriesViewModel(viewModel: CategoriesViewModel): ViewModel
}
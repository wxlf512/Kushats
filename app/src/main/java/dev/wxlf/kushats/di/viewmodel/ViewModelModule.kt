package dev.wxlf.kushats.di.viewmodel

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dev.wxlf.kushats.feature_bag.presentation.viewmodels.BagViewModel
import dev.wxlf.kushats.feature_category.presentation.viewmodels.CategoryViewModel
import dev.wxlf.kushats.feature_category.presentation.viewmodels.ProductDialogViewModel
import dev.wxlf.kushats.feature_main.presentation.viewmodels.MainViewModel

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindsMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CategoryViewModel::class)
    abstract fun bindsCategoryViewModel(viewModel: CategoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProductDialogViewModel::class)
    abstract fun bindsProductDialogViewModel(viewModel: ProductDialogViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BagViewModel::class)
    abstract fun bindsBagViewModel(viewModel: BagViewModel): ViewModel
}
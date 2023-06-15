package dev.wxlf.kushats.di.viewmodel

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dev.wxlf.kushats.feature_bag.presentation.viewmodels.BagViewModel
import dev.wxlf.kushats.feature_catalog.presentation.viewmodels.CatalogViewModel
import dev.wxlf.kushats.feature_catalog.presentation.viewmodels.ProductDialogViewModel
import dev.wxlf.kushats.feature_categories.presentation.viewmodels.CategoriesViewModel

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CategoriesViewModel::class)
    abstract fun bindsCategoriesViewModel(viewModel: CategoriesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CatalogViewModel::class)
    abstract fun bindsCatalogViewModel(viewModel: CatalogViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProductDialogViewModel::class)
    abstract fun bindsProductDialogViewModel(viewModel: ProductDialogViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BagViewModel::class)
    abstract fun bindsBagViewModel(viewModel: BagViewModel): ViewModel
}
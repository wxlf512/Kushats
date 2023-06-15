package dev.wxlf.kushats.feature_catalog.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dev.wxlf.kushats.feature_catalog.presentation.fragments.CatalogFragment
import dev.wxlf.kushats.feature_catalog.presentation.fragments.ProductDialogFragment

@Module
abstract class CatalogModule {

    @ContributesAndroidInjector
    abstract fun contributeCatalogFragment(): CatalogFragment

    @ContributesAndroidInjector
    abstract fun contributeProductDialogFragment(): ProductDialogFragment
}
package dev.wxlf.kushats.feature_catalog.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dev.wxlf.kushats.feature_catalog.presentation.fragments.CatalogFragment

@Module
abstract class CatalogModule {

    @ContributesAndroidInjector
    abstract fun contributeCatalogFragment(): CatalogFragment
}
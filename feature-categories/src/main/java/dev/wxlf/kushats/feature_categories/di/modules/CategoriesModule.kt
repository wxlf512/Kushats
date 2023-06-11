package dev.wxlf.kushats.feature_categories.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dev.wxlf.kushats.feature_categories.presentation.fragments.CategoriesFragment

@Module
abstract class CategoriesModule {

    @ContributesAndroidInjector
    abstract fun contributeCategoriesFragment(): CategoriesFragment
}
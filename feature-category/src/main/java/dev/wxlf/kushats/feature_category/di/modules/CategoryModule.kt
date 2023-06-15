package dev.wxlf.kushats.feature_category.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dev.wxlf.kushats.feature_category.presentation.fragments.CategoryFragment
import dev.wxlf.kushats.feature_category.presentation.fragments.ProductDialogFragment

@Module
abstract class CategoryModule {

    @ContributesAndroidInjector
    abstract fun contributeCategoryFragment(): CategoryFragment

    @ContributesAndroidInjector
    abstract fun contributeProductDialogFragment(): ProductDialogFragment
}
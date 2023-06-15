package dev.wxlf.kushats.feature_bag.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dev.wxlf.kushats.feature_bag.presentation.fragments.BagFragment

@Module
abstract class BagModule {

    @ContributesAndroidInjector
    abstract fun contributeBagFragment(): BagFragment
}
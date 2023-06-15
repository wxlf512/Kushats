package dev.wxlf.kushats.feature_main.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dev.wxlf.kushats.feature_main.presentation.fragments.MainFragment

@Module
abstract class MainModule {

    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): MainFragment
}
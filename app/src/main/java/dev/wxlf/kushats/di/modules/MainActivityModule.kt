package dev.wxlf.kushats.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dev.wxlf.kushats.MainActivity

@Module
abstract class MainActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}
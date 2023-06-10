package dev.wxlf.kushats

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dev.wxlf.kushats.di.AppComponent
import dev.wxlf.kushats.di.DaggerAppComponent
import dev.wxlf.kushats.feature_categories.di.DaggerCategoriesComponent
import javax.inject.Inject

class KushatsApp : Application(), HasAndroidInjector {

    lateinit var appComponent: AppComponent

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .application(this)
            .categoriesComponent(provideCategoriesComponent())
            .build()

        appComponent.inject(this)
    }

    private fun provideCategoriesComponent() =
        DaggerCategoriesComponent.builder().build()
}
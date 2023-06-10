package dev.wxlf.kushats.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import dev.wxlf.kushats.KushatsApp
import dev.wxlf.kushats.core.di.NetworkModule
import dev.wxlf.kushats.feature_categories.di.CategoriesComponent

@AppScope
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        NetworkModule::class
    ],
    dependencies = [
        CategoriesComponent::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface AppComponentBuilder {
        @BindsInstance
        fun application(application: Application): AppComponentBuilder

        fun categoriesComponent(categoriesComponent: CategoriesComponent): AppComponentBuilder

        fun build(): AppComponent
    }

    fun inject(kushatsApp: KushatsApp)
}
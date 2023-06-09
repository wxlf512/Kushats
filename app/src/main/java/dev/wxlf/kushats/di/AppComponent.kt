package dev.wxlf.kushats.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import dev.wxlf.kushats.KushatsApp
import dev.wxlf.kushats.core.di.NetworkModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        NetworkModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface AppComponentBuilder {
        @BindsInstance
        fun application(application: Application): AppComponentBuilder

        fun build(): AppComponent
    }

    fun inject(kushatsApp: KushatsApp)
}
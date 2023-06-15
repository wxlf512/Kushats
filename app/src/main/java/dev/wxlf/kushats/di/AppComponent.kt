package dev.wxlf.kushats.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dev.wxlf.kushats.KushatsApp
import dev.wxlf.kushats.core.di.NetworkModule
import dev.wxlf.kushats.core.di.RoomModule
import dev.wxlf.kushats.di.modules.ContextModule
import dev.wxlf.kushats.di.modules.MainActivityModule
import dev.wxlf.kushats.di.viewmodel.ViewModelFactoryModule
import dev.wxlf.kushats.di.viewmodel.ViewModelModule
import dev.wxlf.kushats.feature_bag.di.modules.BagDataModule
import dev.wxlf.kushats.feature_bag.di.modules.BagModule
import dev.wxlf.kushats.feature_bag.di.modules.BagUseCaseModule
import dev.wxlf.kushats.feature_catalog.di.modules.CatalogDataModule
import dev.wxlf.kushats.feature_catalog.di.modules.CatalogModule
import dev.wxlf.kushats.feature_catalog.di.modules.CatalogUseCaseModule
import dev.wxlf.kushats.feature_categories.di.modules.CategoriesDataModule
import dev.wxlf.kushats.feature_categories.di.modules.CategoriesModule
import dev.wxlf.kushats.feature_categories.di.modules.CategoriesUseCaseModule
import dev.wxlf.kushats.feature_categories.presentation.fragments.CategoriesFragment
import javax.inject.Singleton

@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ContextModule::class,
        MainActivityModule::class,
        //Data
        NetworkModule::class,
        RoomModule::class,
        //ViewModel
        ViewModelFactoryModule::class,
        ViewModelModule::class,
        //Categories
        CategoriesModule::class,
        CategoriesDataModule::class,
        CategoriesUseCaseModule::class,
        //Catalog
        CatalogModule::class,
        CatalogDataModule::class,
        CatalogUseCaseModule::class,
        // Bag
        BagModule::class,
        BagDataModule::class,
        BagUseCaseModule::class
    ]
)
@Singleton
interface AppComponent : AndroidInjector<KushatsApp> {

    @Component.Builder
    interface AppComponentBuilder {
        @BindsInstance
        fun application(application: Application): AppComponentBuilder

        fun build(): AppComponent
    }

    fun inject(categoriesFragment: CategoriesFragment)
}
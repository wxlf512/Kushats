package dev.wxlf.kushats.feature_categories.di

import dagger.Component
import dev.wxlf.kushats.feature_categories.di.modules.DataModule

@Component(
    modules = [
        DataModule::class
    ]
)
@CategoriesScope
interface CategoriesComponent {

    @Component.Builder
    interface CategoriesComponentBuilder {
        fun build(): CategoriesComponent
    }
}
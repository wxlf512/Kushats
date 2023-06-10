package dev.wxlf.kushats.feature_categories.di.modules

import dagger.Module
import dagger.Provides
import dev.wxlf.kushats.feature_categories.di.CategoriesScope
import dev.wxlf.kushats.feature_categories.domain.FoodRepository
import dev.wxlf.kushats.feature_categories.domain.usecases.FetchCategoriesUseCase

@Module
class UseCaseModule {

    @Provides
    @CategoriesScope
    fun provideFetchCategoriesUseCase(foodRepository: FoodRepository): FetchCategoriesUseCase =
        FetchCategoriesUseCase(foodRepository)
}
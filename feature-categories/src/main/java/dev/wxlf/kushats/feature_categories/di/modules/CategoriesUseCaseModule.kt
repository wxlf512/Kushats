package dev.wxlf.kushats.feature_categories.di.modules

import dagger.Module
import dagger.Provides
import dev.wxlf.kushats.feature_categories.domain.FoodRepository
import dev.wxlf.kushats.feature_categories.domain.usecases.FetchCategoriesUseCase
import javax.inject.Singleton

@Module
class CategoriesUseCaseModule {

    @Provides
    @Singleton
    fun provideFetchCategoriesUseCase(foodRepository: FoodRepository): FetchCategoriesUseCase =
        FetchCategoriesUseCase(foodRepository)
}
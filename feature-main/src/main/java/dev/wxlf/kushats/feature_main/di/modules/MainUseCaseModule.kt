package dev.wxlf.kushats.feature_main.di.modules

import dagger.Module
import dagger.Provides
import dev.wxlf.kushats.feature_main.domain.FoodRepository
import dev.wxlf.kushats.feature_main.domain.usecases.FetchCategoriesUseCase
import javax.inject.Singleton

@Module
class MainUseCaseModule {

    @Provides
    @Singleton
    fun provideFetchCategoriesUseCase(foodRepository: FoodRepository): FetchCategoriesUseCase =
        FetchCategoriesUseCase(foodRepository)
}
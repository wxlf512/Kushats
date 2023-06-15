package dev.wxlf.kushats.feature_catalog.di.modules

import dagger.Module
import dagger.Provides
import dev.wxlf.kushats.feature_catalog.domain.repositories.BagRepository
import dev.wxlf.kushats.feature_catalog.domain.repositories.FoodRepository
import dev.wxlf.kushats.feature_catalog.domain.usecases.AddProductToBagUseCase
import dev.wxlf.kushats.feature_catalog.domain.usecases.FetchCategoryUseCase
import dev.wxlf.kushats.feature_catalog.domain.usecases.FetchDishUseCase
import dev.wxlf.kushats.feature_catalog.domain.usecases.FetchDishesUseCase
import javax.inject.Singleton

@Module
class CatalogUseCaseModule {

    @Provides
    @Singleton
    fun provideFetchDishesUseCase(foodRepository: FoodRepository): FetchDishesUseCase =
        FetchDishesUseCase(foodRepository)

    @Provides
    @Singleton
    fun provideAddProductUseCase(bagRepository: BagRepository): AddProductToBagUseCase =
        AddProductToBagUseCase(bagRepository)

    @Provides
    @Singleton
    fun provideFetchCategoryUseCase(foodRepository: FoodRepository): FetchCategoryUseCase =
        FetchCategoryUseCase(foodRepository)

    @Provides
    @Singleton
    fun provideFetchDishUseCase(foodRepository: FoodRepository): FetchDishUseCase =
        FetchDishUseCase(foodRepository)
}
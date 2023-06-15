package dev.wxlf.kushats.feature_category.di.modules

import dagger.Module
import dagger.Provides
import dev.wxlf.kushats.feature_category.domain.repositories.BagRepository
import dev.wxlf.kushats.feature_category.domain.repositories.FoodRepository
import dev.wxlf.kushats.feature_category.domain.usecases.AddProductToBagUseCase
import dev.wxlf.kushats.feature_category.domain.usecases.FetchCategoryUseCase
import dev.wxlf.kushats.feature_category.domain.usecases.FetchDishUseCase
import dev.wxlf.kushats.feature_category.domain.usecases.FetchDishesUseCase
import javax.inject.Singleton

@Module
class CategoryUseCaseModule {

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
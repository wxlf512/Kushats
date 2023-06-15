package dev.wxlf.kushats.feature_bag.di.modules

import dagger.Module
import dagger.Provides
import dev.wxlf.kushats.feature_bag.domain.repositories.BagRepository
import dev.wxlf.kushats.feature_bag.domain.repositories.FoodRepository
import dev.wxlf.kushats.feature_bag.domain.usecases.ChangeProductCountUseCase
import dev.wxlf.kushats.feature_bag.domain.usecases.FetchDishesUseCase
import javax.inject.Singleton

@Module
class BagUseCaseModule {

    @Provides
    @Singleton
    fun provideFetchDishesUseCase(
        foodRepository: FoodRepository,
        bagRepository: BagRepository
    ): FetchDishesUseCase =
        FetchDishesUseCase(foodRepository, bagRepository)

    @Provides
    @Singleton
    fun provideChangeProductCountUseCase(bagRepository: BagRepository): ChangeProductCountUseCase =
        ChangeProductCountUseCase(bagRepository)
}
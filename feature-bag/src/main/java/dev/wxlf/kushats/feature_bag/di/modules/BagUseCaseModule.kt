package dev.wxlf.kushats.feature_bag.di.modules

import dagger.Module
import dagger.Provides
import dev.wxlf.kushats.feature_bag.domain.repositories.BagRepository
import dev.wxlf.kushats.feature_bag.domain.repositories.FoodRepository
import dev.wxlf.kushats.feature_bag.domain.usecases.DecrementProductCountUseCase
import dev.wxlf.kushats.feature_bag.domain.usecases.FetchDishesUseCase
import dev.wxlf.kushats.feature_bag.domain.usecases.IncrementProductCountUseCase
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
    fun provideIncrementProductCountUseCase(bagRepository: BagRepository): IncrementProductCountUseCase =
        IncrementProductCountUseCase(bagRepository)

    @Provides
    @Singleton
    fun provideDecrementProductCountUseCase(bagRepository: BagRepository): DecrementProductCountUseCase =
        DecrementProductCountUseCase(bagRepository)
}
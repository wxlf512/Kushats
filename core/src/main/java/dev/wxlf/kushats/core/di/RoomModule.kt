package dev.wxlf.kushats.core.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dev.wxlf.kushats.core.R
import dev.wxlf.kushats.core.data.room.BagDao
import dev.wxlf.kushats.core.data.room.KushatsDatabase
import javax.inject.Singleton

@Module
class RoomModule {
    
    @Provides
    @Singleton
    fun provideKushatsDatabase(context: Context): KushatsDatabase =
        Room.databaseBuilder(
            context = context,
            KushatsDatabase::class.java,
            context.getString(R.string.kushats_database)
        ).build()

    @Provides
    @Singleton
    fun provideBagDao(kushatsDatabase: KushatsDatabase): BagDao = kushatsDatabase.getBagDao()
}
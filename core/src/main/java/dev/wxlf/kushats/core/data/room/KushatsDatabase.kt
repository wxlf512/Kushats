package dev.wxlf.kushats.core.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.wxlf.kushats.core.data.entities.ProductEntity

@Database(
    version = 1,
    exportSchema = true,
    entities = [ProductEntity::class]
)
abstract class KushatsDatabase : RoomDatabase() {

    abstract fun getBagDao(): BagDao

}
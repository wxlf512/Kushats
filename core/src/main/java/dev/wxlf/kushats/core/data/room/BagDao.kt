package dev.wxlf.kushats.core.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import dev.wxlf.kushats.core.data.entities.ProductEntity
import dev.wxlf.kushats.core.data.entities.ProductEntity.Companion.BAG_TABLE

@Dao
interface BagDao {

    @Insert(ProductEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(productEntity: ProductEntity)

    @Query("SELECT * FROM $BAG_TABLE")
    suspend fun loadBag(): List<ProductEntity>

    @Update(ProductEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateProduct(productEntity: ProductEntity)

    @Delete(ProductEntity::class)
    suspend fun deleteProduct(productEntity: ProductEntity)
}

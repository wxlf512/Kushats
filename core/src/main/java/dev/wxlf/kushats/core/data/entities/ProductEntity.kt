package dev.wxlf.kushats.core.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.wxlf.kushats.core.data.entities.ProductEntity.Companion.BAG_TABLE

@Entity(tableName = BAG_TABLE)
data class ProductEntity(
    @PrimaryKey
    @ColumnInfo("id") val id: Int,
    @ColumnInfo("count") var count: Int
) {
    companion object {
        const val BAG_TABLE = "bag_table"
    }
}
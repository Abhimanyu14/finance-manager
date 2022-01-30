package com.makeappssimple.abhimanyu.financemanager.android.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.makeappssimple.abhimanyu.financemanager.android.data.local.database.converters.CategoryIdsConverter

@Entity(tableName = "category_table")
data class Category(
    @ColumnInfo(name = "parent_category")
    val parentCategoryId: Int? = null,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "sub_categories")
    @TypeConverters(CategoryIdsConverter::class)
    val subCategoryIds: List<Int>? = null,
    val description: String = "",
    val title: String,
    @ColumnInfo(name = "transaction_type")
    val transactionType: TransactionType,
)

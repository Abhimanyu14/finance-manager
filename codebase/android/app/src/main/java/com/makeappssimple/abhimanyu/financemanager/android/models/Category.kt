package com.makeappssimple.abhimanyu.financemanager.android.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.makeappssimple.abhimanyu.financemanager.android.data.converters.CategoriesConverter

@Entity(tableName = "category_table")
data class Category(
    @ColumnInfo(name = "parent_category")
    val parentCategory: Category? = null,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "sub_categories")
    @TypeConverters(CategoriesConverter::class)
    val subCategories: List<Category>? = null,
    val description: String = "",
    val title: String,
    @ColumnInfo(name = "transaction_type")
    val transactionType: TransactionType,
)

package com.makeappssimple.abhimanyu.financemanager.android.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.makeappssimple.abhimanyu.financemanager.android.core.database.converters.IntListConverter
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "category_table")
data class Category(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "parent_category")
    @Json(name = "parent_category")
    val parentCategoryId: Int? = null,

    @ColumnInfo(name = "sub_categories")
    @Json(name = "sub_categories")
    @TypeConverters(IntListConverter::class)
    val subCategoryIds: List<Int>? = null,

    val description: String = "",

    val emoji: String,

    val title: String,

    @ColumnInfo(name = "transaction_type")
    @Json(name = "transaction_type")
    val transactionType: TransactionType,
)

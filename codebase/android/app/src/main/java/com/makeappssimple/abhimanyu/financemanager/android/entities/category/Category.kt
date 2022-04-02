package com.makeappssimple.abhimanyu.financemanager.android.entities.category

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.makeappssimple.abhimanyu.financemanager.android.data.local.database.converters.CategoryIdsConverter
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.TransactionType
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "category_table")
data class Category(
    @ColumnInfo(name = "parent_category")
    @SerializedName(value = "parent_category")
    @Json(name = "parent_category")
    val parentCategoryId: Int? = null,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "sub_categories")
    @SerializedName(value = "sub_categories")
    @Json(name = "sub_categories")
    @TypeConverters(CategoryIdsConverter::class)
    val subCategoryIds: List<Int>? = null,
    val description: String = "",
    val title: String,
    @ColumnInfo(name = "transaction_type")
    @SerializedName(value = "transaction_type")
    @Json(name = "transaction_type")
    val transactionType: TransactionType,
)

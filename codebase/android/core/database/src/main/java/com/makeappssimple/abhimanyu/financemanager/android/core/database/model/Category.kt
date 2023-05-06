package com.makeappssimple.abhimanyu.financemanager.android.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.makeappssimple.abhimanyu.financemanager.android.core.database.converters.IntListConverter
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
@Entity(tableName = "category_table")
data class Category @OptIn(ExperimentalSerializationApi::class) constructor(
    @EncodeDefault
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    // TODO(Abhi): Fix parent category id column name
    @ColumnInfo(name = "parent_category")
    @SerialName(value = "parent_category")
    val parentCategoryId: Int? = null,

    // TODO(Abhi): Fix sub category ids column name
    @ColumnInfo(name = "sub_categories")
    @SerialName(value = "sub_categories")
    @TypeConverters(IntListConverter::class)
    val subCategoryIds: List<Int>? = null,

    @EncodeDefault
    val description: String = "",

    val emoji: String,

    val title: String,

    @ColumnInfo(name = "transaction_type")
    @SerialName(value = "transaction_type")
    @JsonNames("transactionType")
    val transactionType: TransactionType,
)

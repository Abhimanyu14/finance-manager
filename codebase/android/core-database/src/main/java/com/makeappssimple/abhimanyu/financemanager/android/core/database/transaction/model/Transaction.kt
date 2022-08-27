package com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.makeappssimple.abhimanyu.financemanager.android.core.database.amount.model.Amount
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "transaction_table")
data class Transaction(
    val amount: Amount,
    @ColumnInfo(name = "category_id")
    @Json(name = "category_id")
    val categoryId: Int? = null,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "source_from_id")
    @Json(name = "source_from_id")
    val sourceFromId: Int? = null,
    @ColumnInfo(name = "source_to_id")
    @Json(name = "source_to_id")
    val sourceToId: Int? = null,
    val description: String = "",
    val title: String,
    @ColumnInfo(name = "creation_timestamp")
    @Json(name = "creation_timestamp")
    val creationTimestamp: Long,
    @ColumnInfo(name = "transaction_timestamp")
    @Json(name = "transaction_timestamp")
    val transactionTimestamp: Long,
    @ColumnInfo(name = "transaction_for_id")
    @Json(name = "transaction_for_id")
    val transactionForId: Int = 1,
    @ColumnInfo(name = "transaction_type")
    @Json(name = "transaction_type")
    val transactionType: TransactionType = TransactionType.EXPENSE,
)

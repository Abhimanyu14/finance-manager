package com.makeappssimple.abhimanyu.financemanager.android.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.makeappssimple.abhimanyu.financemanager.android.core.database.converters.IntListConverter
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = "transaction_table")
@Serializable
data class Transaction(
    val amount: Amount,

    @ColumnInfo(name = "category_id")
    @SerialName(value = "category_id")
    val categoryId: Int? = null,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "original_transaction_id")
    @SerialName(value = "original_transaction_id")
    val originalTransactionId: Int? = null,

    @ColumnInfo(name = "source_from_id")
    @SerialName(value = "source_from_id")
    val sourceFromId: Int? = null,

    @ColumnInfo(name = "source_to_id")
    @SerialName(value = "source_to_id")
    val sourceToId: Int? = null,

    @ColumnInfo(name = "transaction_for_id")
    @SerialName(value = "transaction_for_id")
    val transactionForId: Int = 1,

    @ColumnInfo(name = "refund_transaction_ids")
    @SerialName(value = "refund_transaction_ids")
    @TypeConverters(IntListConverter::class)
    val refundTransactionIds: List<Int>? = null,

    @ColumnInfo(name = "creation_timestamp")
    @SerialName(value = "creation_timestamp")
    val creationTimestamp: Long,

    @ColumnInfo(name = "transaction_timestamp")
    @SerialName(value = "transaction_timestamp")
    val transactionTimestamp: Long,

    val description: String = "",

    val title: String,

    @ColumnInfo(name = "transaction_type")
    @SerialName(value = "transaction_type")
    val transactionType: TransactionType = TransactionType.EXPENSE,
)

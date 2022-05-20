package com.makeappssimple.abhimanyu.financemanager.android.entities.transaction

import androidx.compose.ui.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.makeappssimple.abhimanyu.financemanager.android.entities.amount.Amount
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.OnTertiaryContainer
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
    @ColumnInfo(name = "transaction_for")
    @Json(name = "transaction_for")
    val transactionFor: TransactionFor = TransactionFor.SELF,
    @ColumnInfo(name = "transaction_type")
    @Json(name = "transaction_type")
    val transactionType: TransactionType = TransactionType.EXPENSE,
)

val Transaction.amountTextColor: Color
    get() = when (this.transactionType) {
        TransactionType.INCOME -> {
            OnTertiaryContainer
        }
        TransactionType.EXPENSE -> {
            Color.Red
        }
        TransactionType.TRANSFER -> {
            Color.DarkGray
        }
        TransactionType.ADJUSTMENT -> {
            when {
                this.amount.value > 0 -> {
                    OnTertiaryContainer
                }
                this.amount.value < 0 -> {
                    Color.Red
                }
                else -> {
                    Color.DarkGray
                }
            }
        }
    }

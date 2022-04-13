package com.makeappssimple.abhimanyu.financemanager.android.entities.transaction

import androidx.compose.ui.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.makeappssimple.abhimanyu.financemanager.android.entities.amount.Amount
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.OnTertiaryContainer
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
enum class TransactionFor(
    val title: String,
) {
    SELF(
        title = "Self",
    ),
    COMMON(
        title = "Common",
    ),
    OTHERS(
        title = "Others",
    ),
}

@JsonClass(generateAdapter = false)
enum class TransactionType(
    val title: String,
) {
    INCOME(
        title = "Income",
    ),
    EXPENSE(
        title = "Expense",
    ),
    TRANSFER(
        title = "Transfer",
    ),
    ADJUSTMENT(
        title = "Adjustment",
    ),
    /*
    // TODO-Abhi: Add Loans later
    LOAN(
        title = "Loan",
    ),
    REPAYMENT(
        title = "Repayment",
    ),
    */
}

@JsonClass(generateAdapter = true)
@Entity(tableName = "transaction_table")
data class Transaction(
    val amount: Amount,
    @ColumnInfo(name = "category_id")
    @SerializedName(value = "category_id")
    val categoryId: Int = 0,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "source_from_id")
    @SerializedName(value = "source_from_id")
    val sourceFromId: Int = 0,
    @ColumnInfo(name = "source_to_id")
    @SerializedName(value = "source_to_id")
    val sourceToId: Int = 0,
    val description: String = "",
    val title: String,
    @ColumnInfo(name = "creation_timestamp")
    @SerializedName(value = "creation_timestamp")
    val creationTimestamp: Long,
    @ColumnInfo(name = "transaction_timestamp")
    @SerializedName(value = "transaction_timestamp")
    val transactionTimestamp: Long,
    @ColumnInfo(name = "transaction_for")
    @SerializedName(value = "transaction_for")
    val transactionFor: TransactionFor = TransactionFor.SELF,
    @ColumnInfo(name = "transaction_type")
    @SerializedName(value = "transaction_type")
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

package com.makeappssimple.abhimanyu.financemanager.android.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

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
    //    TODO-Abhi: Add Loans later
    //    LOAN(
    //        title = "Loan",
    //    ),
    //    REPAYMENT(
    //        title = "Repayment",
    //    ),
}

@Entity(tableName = "transaction_table")
data class Transaction(
    val amount: Amount,
    @ColumnInfo(name = "category_id")
    val categoryId: Int = 0,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "source_id")
    val sourceId: Int = 0,
    val description: String = "",
    val title: String,
    @ColumnInfo(name = "creation_timestamp")
    val creationTimestamp: Long,
    @ColumnInfo(name = "transaction_timestamp")
    val transactionTimestamp: Long,
    @ColumnInfo(name = "transaction_for")
    val transactionFor: TransactionFor = TransactionFor.SELF,
    @ColumnInfo(name = "transaction_type")
    val transactionType: TransactionType = TransactionType.EXPENSE,
)

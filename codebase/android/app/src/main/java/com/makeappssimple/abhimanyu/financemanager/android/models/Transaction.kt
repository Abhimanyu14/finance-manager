package com.makeappssimple.abhimanyu.financemanager.android.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

enum class TransactionFor {
    SELF,
    COMMON,
    OTHERS,
}

enum class TransactionType {
    INCOME,
    EXPENSE,
    TRANSFER,
    LOAN,
    REPAYMENT,
}

@Entity(tableName = "transaction_table")
data class Transaction(
    val amount: Amount,
    val category: Category,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val description: String = "",
    val title: String,
    @ColumnInfo(name = "creation_timestamp")
    val creationTimestamp: Double,
    @ColumnInfo(name = "transaction_timestamp")
    val transactionTimestamp: Double,
    @ColumnInfo(name = "transaction_for")
    val transactionFor: TransactionFor = TransactionFor.SELF,
    @ColumnInfo(name = "transaction_type")
    val transactionType: TransactionType = TransactionType.EXPENSE,
)

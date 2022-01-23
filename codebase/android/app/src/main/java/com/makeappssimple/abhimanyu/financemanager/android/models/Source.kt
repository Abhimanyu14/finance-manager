package com.makeappssimple.abhimanyu.financemanager.android.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

enum class SourceType(
    val title: String,
) {
    BANK(
        title = "Bank",
    ),
    CASH(
        title = "Cash",
    ),
    E_WALLET(
        title = "E-Wallet",
    ),
}

@Entity(tableName = "source_table")
data class Source(
    @ColumnInfo(name = "balance_amount")
    val balanceAmount: Amount = Amount(
        value = 0F,
    ),
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val type: SourceType = SourceType.CASH,
    val name: String,
)

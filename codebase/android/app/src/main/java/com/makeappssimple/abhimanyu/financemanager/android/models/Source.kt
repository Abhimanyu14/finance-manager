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
        value = 0,
    ),
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val type: SourceType = SourceType.CASH,
    val name: String,
)

val SourceType.sortOrder: Int
    get() = when (this) {
        SourceType.CASH -> {
            1
        }
        SourceType.BANK -> {
            2
        }
        SourceType.E_WALLET -> {
            3
        }
    }

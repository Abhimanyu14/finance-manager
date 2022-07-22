package com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.makeappssimple.abhimanyu.financemanager.android.core.database.amount.model.Amount
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
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

@JsonClass(generateAdapter = true)
@Entity(tableName = "source_table")
data class Source(
    @ColumnInfo(name = "balance_amount")
    @Json(name = "balance_amount")
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

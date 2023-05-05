package com.makeappssimple.abhimanyu.financemanager.android.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.makeappssimple.abhimanyu.financemanager.android.core.model.SourceType
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

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

fun Source.updateBalanceAmount(
    updatedBalanceAmount: Long,
): Source {
    return this.copy(
        balanceAmount = this.balanceAmount.copy(
            value = updatedBalanceAmount,
        )
    )
}

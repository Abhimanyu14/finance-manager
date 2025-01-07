package com.makeappssimple.abhimanyu.financemanager.android.cre.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.AccountType
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// TODO(Abhi): Clean up deprecated class
@Deprecated(
    message = "Maintained only for migration",
    replaceWith = ReplaceWith(
        expression = "AccountEntity",
    ),
)
@Entity(tableName = "source_table")
@Serializable
public data class SourceEntity(
    @EncodeDefault
    @ColumnInfo(name = "balance_amount")
    @SerialName(value = "balance_amount")
    val balanceAmount: AmountEntity = AmountEntity(
        value = 0,
    ),

    @EncodeDefault
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @EncodeDefault
    val type: AccountType = AccountType.CASH,

    val name: String,
)

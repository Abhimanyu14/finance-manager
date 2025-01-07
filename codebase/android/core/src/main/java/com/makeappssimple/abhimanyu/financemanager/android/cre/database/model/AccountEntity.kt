package com.makeappssimple.abhimanyu.financemanager.android.cre.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.AccountType
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = "account_table")
@Serializable
public data class AccountEntity(
    @EncodeDefault
    @ColumnInfo(name = "balance_amount")
    @SerialName(value = "balance_amount")
    val balanceAmount: AmountEntity = AmountEntity(
        value = 0,
    ),

    @EncodeDefault
    @ColumnInfo(name = "is_archived", defaultValue = "0")
    @SerialName(value = "is_archived")
    val isArchived: Boolean = false,

    @EncodeDefault
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @EncodeDefault
    @ColumnInfo(name = "type")
    @SerialName(value = "type")
    val type: AccountType = AccountType.CASH,

    @EncodeDefault
    @ColumnInfo(name = "minimum_account_balance_amount")
    @SerialName(value = "minimum_account_balance_amount")
    val minimumAccountBalanceAmount: AmountEntity? = null,

    @ColumnInfo(name = "name")
    @SerialName(value = "name")
    val name: String,
)

public fun AccountEntity.updateBalanceAmount(
    updatedBalanceAmount: Long,
): AccountEntity {
    return this.copy(
        balanceAmount = this.balanceAmount.copy(
            value = updatedBalanceAmount,
        )
    )
}

public fun AccountEntity.asExternalModel(): Account {
    return Account(
        balanceAmount = balanceAmount.asExternalModel(),
        id = id,
        type = type,
        minimumAccountBalanceAmount = minimumAccountBalanceAmount?.asExternalModel(),
        name = name,
    )
}

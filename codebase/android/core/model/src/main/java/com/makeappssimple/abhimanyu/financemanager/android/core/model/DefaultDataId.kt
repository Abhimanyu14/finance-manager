package com.makeappssimple.abhimanyu.financemanager.android.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class DefaultDataId(
    @SerialName(value = "expense_category")
    val expenseCategory: Int = 0,

    @SerialName(value = "income_category")
    val incomeCategory: Int = 0,

    @SerialName(value = "investment_category")
    val investmentCategory: Int = 0,

    @SerialName(value = "account")
    val account: Int = 0,
)

package com.makeappssimple.abhimanyu.financemanager.android.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DefaultDataId(
    @SerialName(value = "expense_category")
    val expenseCategory: Int = 0,

    @SerialName(value = "income_category")
    val incomeCategory: Int = 0,

    @SerialName(value = "investment_category")
    val investmentCategory: Int = 0,

    // TODO(Abhi) - Source to account rename migration
    @SerialName(value = "source")
    val source: Int = 0,
)

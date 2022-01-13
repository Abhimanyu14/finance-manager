package com.makeappssimple.abhimanyu.financemanager.android.models

data class Source(
    val id: Int,
    val name: String,
    val type: SourceType = SourceType.CASH,
    val balanceAmount: Amount,
)

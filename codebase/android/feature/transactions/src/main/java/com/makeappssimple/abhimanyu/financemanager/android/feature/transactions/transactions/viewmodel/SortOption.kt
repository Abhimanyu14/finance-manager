package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull

enum class SortOption(
    val title: String,
) {
    AMOUNT_ASC(
        title = "Amount Asc",
    ),
    AMOUNT_DESC(
        title = "Amount Desc",
    ),
    LATEST_FIRST(
        title = "Latest First",
    ),
    OLDEST_FIRST(
        title = "Oldest First",
    ),
}

fun SortOption?.orDefault(): SortOption {
    return if (this.isNull()) {
        SortOption.LATEST_FIRST
    } else {
        this
    }
}

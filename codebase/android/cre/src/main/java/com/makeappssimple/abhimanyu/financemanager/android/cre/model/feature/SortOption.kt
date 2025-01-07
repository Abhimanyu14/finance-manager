package com.makeappssimple.abhimanyu.financemanager.android.cre.model.feature

import com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions.isNull

public enum class SortOption(
    public val title: String,
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

public fun SortOption?.orDefault(): SortOption {
    return if (this.isNull()) {
        SortOption.LATEST_FIRST
    } else {
        this
    }
}

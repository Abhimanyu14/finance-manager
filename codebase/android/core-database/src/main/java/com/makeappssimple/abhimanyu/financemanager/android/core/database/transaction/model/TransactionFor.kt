package com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
enum class TransactionFor(
    val title: String,
) {
    SELF(
        title = "Self",
    ),
    COMMON(
        title = "Common",
    ),
    OTHERS(
        title = "Others",
    ),
}

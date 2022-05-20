package com.makeappssimple.abhimanyu.financemanager.android.entities.transaction

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

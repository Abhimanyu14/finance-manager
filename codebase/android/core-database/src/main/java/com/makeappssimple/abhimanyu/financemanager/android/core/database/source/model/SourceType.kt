package com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
enum class SourceType(
    val title: String,
) {
    BANK(
        title = "Bank",
    ),
    CASH(
        title = "Cash",
    ),
    E_WALLET(
        title = "E-Wallet",
    ),
}

val SourceType.sortOrder: Int
    get() = when (this) {
        SourceType.CASH -> {
            1
        }

        SourceType.BANK -> {
            2
        }

        SourceType.E_WALLET -> {
            3
        }
    }

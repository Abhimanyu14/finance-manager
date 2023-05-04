package com.makeappssimple.abhimanyu.financemanager.android.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkEmoji(
    val character: String,
    val codePoint: String,
    val group: String,
    val unicodeName: String,
)

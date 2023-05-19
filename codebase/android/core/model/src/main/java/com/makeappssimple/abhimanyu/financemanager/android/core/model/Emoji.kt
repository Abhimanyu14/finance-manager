package com.makeappssimple.abhimanyu.financemanager.android.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Emoji(
    val character: String,

    @SerialName(value = "code_point")
    val codePoint: String,

    val group: String,

    @SerialName(value = "unicode_name")
    val unicodeName: String,
)

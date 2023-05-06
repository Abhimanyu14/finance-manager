package com.makeappssimple.abhimanyu.financemanager.android.core.network.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class NetworkEmoji @OptIn(ExperimentalSerializationApi::class) constructor(
    val character: String,

    @SerialName("code_point")
    @JsonNames("codePoint")
    val codePoint: String,

    val group: String,

    @SerialName("unicode_name")
    @JsonNames("unicodeName")
    val unicodeName: String,
)

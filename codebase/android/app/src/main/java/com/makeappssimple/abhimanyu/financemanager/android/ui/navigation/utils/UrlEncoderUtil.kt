package com.makeappssimple.abhimanyu.financemanager.android.ui.navigation.utils

import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun encodeString(
    string: String,
): String {
    return URLEncoder.encode(
        string,
        StandardCharsets.UTF_8.toString(),
    )
}

fun decodeString(
    string: String?,
    defaultValue: String = "",
): String {
    string?.let {
        return URLDecoder.decode(
            string,
            StandardCharsets.UTF_8.toString(),
        )
    }
    return defaultValue
}


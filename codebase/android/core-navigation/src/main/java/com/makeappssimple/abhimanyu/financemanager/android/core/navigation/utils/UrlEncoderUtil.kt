package com.makeappssimple.abhimanyu.financemanager.android.core.navigation.utils

import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class UrlEncoderUtil {
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
        string ?: return defaultValue
        return URLDecoder.decode(
            string,
            StandardCharsets.UTF_8.toString(),
        )
    }
}

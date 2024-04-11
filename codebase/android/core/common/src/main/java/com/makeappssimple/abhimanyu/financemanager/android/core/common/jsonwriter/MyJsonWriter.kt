package com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonwriter

import android.net.Uri

interface MyJsonWriter {
    fun writeJsonToFile(
        jsonString: String,
        uri: Uri,
    ): Boolean
}

package com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonwriter

import android.net.Uri

interface JsonWriter {
    fun writeJsonToFile(
        jsonString: String,
        uri: Uri,
    )
}

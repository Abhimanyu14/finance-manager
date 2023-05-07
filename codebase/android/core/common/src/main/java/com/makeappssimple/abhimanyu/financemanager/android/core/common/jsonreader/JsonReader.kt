package com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonreader

import android.net.Uri

interface JsonReader {
    fun readJsonFromAssets(
        fileName: String,
    ): String?

    fun readJsonFromFile(
        uri: Uri,
    ): String?
}

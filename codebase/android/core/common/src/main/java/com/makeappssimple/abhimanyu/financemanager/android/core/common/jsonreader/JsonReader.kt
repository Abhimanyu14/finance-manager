package com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonreader

import android.content.Context

interface JsonReader {
    fun readJsonFileFromAssets(
        context: Context,
        fileName: String,
    ): String?
}

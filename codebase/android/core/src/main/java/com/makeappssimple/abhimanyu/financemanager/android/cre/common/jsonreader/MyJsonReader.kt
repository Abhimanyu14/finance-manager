package com.makeappssimple.abhimanyu.financemanager.android.cre.common.jsonreader

import android.net.Uri

public interface MyJsonReader {
    public fun readJsonFromAssets(
        fileName: String,
    ): String?

    public fun readJsonFromFile(
        uri: Uri,
    ): String?
}

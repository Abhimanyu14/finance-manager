package com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonreader

import android.net.Uri

public interface JsonReaderKit {
    public fun readJsonFromAssets(
        fileName: String,
    ): String?

    public fun readJsonFromFile(
        uri: Uri,
    ): String?
}

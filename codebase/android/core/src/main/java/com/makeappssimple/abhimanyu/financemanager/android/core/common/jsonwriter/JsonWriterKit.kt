package com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonwriter

import android.net.Uri

public interface JsonWriterKit {
    public fun writeJsonToFile(
        jsonString: String,
        uri: Uri,
    ): Boolean
}

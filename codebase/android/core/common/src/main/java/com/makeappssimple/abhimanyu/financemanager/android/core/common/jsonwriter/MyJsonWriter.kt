package com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonwriter

import android.net.Uri

public interface MyJsonWriter {
    public fun writeJsonToFile(
        jsonString: String,
        uri: Uri,
    ): Boolean
}

package com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonwriter.fake

import android.net.Uri
import com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonwriter.JsonWriterKit

public class FakeJsonWriterKitImpl : JsonWriterKit {
    override fun writeJsonToFile(
        jsonString: String,
        uri: Uri,
    ): Boolean {
        // No action required
        return false
    }
}

package com.makeappssimple.abhimanyu.financemanager.android.cre.common.jsonwriter.fake

import android.net.Uri
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.jsonwriter.MyJsonWriter

public class FakeMyJsonWriterImpl : MyJsonWriter {
    override fun writeJsonToFile(
        jsonString: String,
        uri: Uri,
    ): Boolean {
        // No action required
        return false
    }
}

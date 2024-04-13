package com.makeappssimple.abhimanyu.financemanager.android.core.testing.jsonreader

import android.net.Uri
import com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonreader.MyJsonReader

public class TestMyJsonReader : MyJsonReader {
    override fun readJsonFromAssets(
        fileName: String,
    ): String? {
        return null
    }

    override fun readJsonFromFile(
        uri: Uri,
    ): String? {
        return null
    }
}

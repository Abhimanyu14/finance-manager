package com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonreader.fake

import android.net.Uri
import com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonreader.MyJsonReader

class FakeMyJsonReaderImpl : MyJsonReader {
    private val assetsJson = """
        {
          "source": "assets"
        }
    """.trimIndent()
    private val fileJson = """
        {
          "source": "assets"
        }
    """.trimIndent()

    override fun readJsonFromAssets(
        fileName: String,
    ): String {
        return assetsJson
    }

    override fun readJsonFromFile(
        uri: Uri,
    ): String {
        return fileJson
    }
}

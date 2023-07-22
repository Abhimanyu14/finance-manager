package com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonreader.fake

import android.net.Uri
import com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonreader.MyJsonReader

class FakeMyJsonReaderImpl : MyJsonReader {
    private val assetsJson = """
        {
          "account": "assets"
        }
    """.trimIndent()
    private val fileJson = """
        {
          "account": "assets"
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

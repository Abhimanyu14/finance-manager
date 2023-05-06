package com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonreader

import android.content.Context
import javax.inject.Inject
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

class JsonReaderImpl @Inject constructor() : JsonReader {
    override fun readJsonFileFromAssets(
        context: Context,
        fileName: String,
    ): String? {
        val json = try {
            val inputStream: InputStream = context.assets.open(fileName)
            val size: Int = inputStream.available()
            val byteArray = ByteArray(size)
            inputStream.read(byteArray)
            inputStream.close()
            String(
                bytes = byteArray,
                charset = Charset.forName("UTF-8"),
            )
        } catch (
            exception: IOException,
        ) {
            exception.printStackTrace()
            null
        }
        return json
    }
}

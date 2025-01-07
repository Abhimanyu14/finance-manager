package com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonreader

import android.content.Context
import android.net.Uri
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.Charset

public class MyJsonReaderImpl(
    private val context: Context,
) : MyJsonReader {
    override fun readJsonFromAssets(
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

    override fun readJsonFromFile(
        uri: Uri,
    ): String? {
        try {
            val contentResolver = context.contentResolver
            contentResolver.openInputStream(uri)?.use { inputStream ->
                BufferedReader(InputStreamReader(inputStream)).use { bufferedReader ->
                    return readFromBufferedReader(
                        bufferedReader = bufferedReader,
                    )
                }
            }
            return null
        } catch (
            fileNotFoundException: FileNotFoundException,
        ) {
            fileNotFoundException.printStackTrace()
            return null
        } catch (
            ioException: IOException,
        ) {
            ioException.printStackTrace()
            return null
        }
    }

    private fun readFromBufferedReader(
        bufferedReader: BufferedReader,
    ): String {
        val stringBuilder = StringBuilder()
        var line: String? = bufferedReader.readLine()
        while (line.isNotNull()) {
            stringBuilder.append(line)
            line = bufferedReader.readLine()
        }
        return stringBuilder.toString()
    }
}

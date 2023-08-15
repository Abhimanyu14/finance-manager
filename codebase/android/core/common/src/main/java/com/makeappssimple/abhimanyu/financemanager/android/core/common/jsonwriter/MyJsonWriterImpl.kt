package com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonwriter

import android.content.Context
import android.net.Uri
import java.io.FileOutputStream

class MyJsonWriterImpl(
    private val context: Context,
) : MyJsonWriter {
    override fun writeJsonToFile(
        jsonString: String,
        uri: Uri,
    ) {
        try {
            val contentResolver = context.contentResolver
            contentResolver.openFileDescriptor(uri, "w")?.use {
                FileOutputStream(it.fileDescriptor).use { fileOutputStream ->
                    fileOutputStream.write(jsonString.toByteArray())
                }
            }
        } catch (
            exception: Exception,
        ) {
            exception.printStackTrace()
        }
    }
}

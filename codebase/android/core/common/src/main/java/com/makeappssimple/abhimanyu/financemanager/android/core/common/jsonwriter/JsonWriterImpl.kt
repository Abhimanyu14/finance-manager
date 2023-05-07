package com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonwriter

import android.content.Context
import android.net.Uri
import javax.inject.Inject
import java.io.FileOutputStream

class JsonWriterImpl @Inject constructor(
    private val context: Context,
) : JsonWriter {
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

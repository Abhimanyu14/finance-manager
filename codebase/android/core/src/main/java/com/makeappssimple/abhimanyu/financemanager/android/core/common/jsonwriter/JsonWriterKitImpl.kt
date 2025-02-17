package com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonwriter

import android.content.Context
import android.net.Uri
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

private object MyJsonWriterImplConstants {
    const val WRITE_MODE = "w"
}

public class JsonWriterKitImpl(
    private val context: Context,
) : JsonWriterKit {
    override fun writeJsonToFile(
        jsonString: String,
        uri: Uri,
    ): Boolean {
        return try {
            context.contentResolver.openFileDescriptor(
                uri,
                MyJsonWriterImplConstants.WRITE_MODE,
            )?.use {
                FileOutputStream(it.fileDescriptor).use { fileOutputStream ->
                    fileOutputStream.write(jsonString.toByteArray())
                }
            } ?: return false
            true
        } catch (
            fileNotFoundException: FileNotFoundException,
        ) {
            fileNotFoundException.printStackTrace()
            false
        } catch (
            ioException: IOException,
        ) {
            ioException.printStackTrace()
            false
        }
    }
}

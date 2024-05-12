package com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonwriter

import android.content.Context
import android.net.Uri
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

private object MyJsonWriterImplConstant {
    const val WRITE_MODE = "w"
}

public class MyJsonWriterImpl(
    private val context: Context,
) : MyJsonWriter {
    override fun writeJsonToFile(
        jsonString: String,
        uri: Uri,
    ): Boolean {
        return try {
            context.contentResolver.openFileDescriptor(
                uri,
                MyJsonWriterImplConstant.WRITE_MODE,
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

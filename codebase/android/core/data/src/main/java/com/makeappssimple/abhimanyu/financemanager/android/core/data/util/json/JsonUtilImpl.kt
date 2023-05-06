package com.makeappssimple.abhimanyu.financemanager.android.core.data.util.json

import android.content.Context
import android.net.Uri
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNullOrBlank
import com.makeappssimple.abhimanyu.financemanager.android.core.data.model.DatabaseBackupData
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader

class JsonUtilImpl(
    private val context: Context,
) : JsonUtil {
    override fun readDatabaseBackupDataFromFile(
        uri: Uri,
    ): DatabaseBackupData? {
        try {
            val contentResolver = context.contentResolver
            val stringBuilder = StringBuilder()
            contentResolver.openInputStream(uri)?.use { inputStream ->
                BufferedReader(InputStreamReader(inputStream)).use { bufferedReader ->
                    var line: String? = bufferedReader.readLine()
                    while (line.isNotNull()) {
                        stringBuilder.append(line)
                        line = bufferedReader.readLine()
                    }
                }
            }

            val jsonString = stringBuilder.toString()
            return if (jsonString.isNotNullOrBlank()) {
                Json.decodeFromString<DatabaseBackupData>(
                    string = stringBuilder.toString(),
                )
            } else {
                null
            }
        } catch (
            exception: FileNotFoundException,
        ) {
            exception.printStackTrace()
            return null
        } catch (
            exception: IOException,
        ) {
            exception.printStackTrace()
            return null
        } catch (
            exception: Exception,
        ) {
            exception.printStackTrace()
            return null
        }
    }

    override fun writeDatabaseBackupDataToFile(
        uri: Uri,
        databaseBackupData: DatabaseBackupData,
    ) {
        val jsonString = try {
            Json.encodeToString(
                value = databaseBackupData,
            )
        } catch (
            exception: Exception,
        ) {
            exception.printStackTrace()
            ""
        }
        writeJsonToFile(
            uri = uri,
            jsonString = jsonString,
        )
    }

    private fun writeJsonToFile(
        uri: Uri,
        jsonString: String,
    ) {
        try {
            val contentResolver = context.contentResolver
            contentResolver.openFileDescriptor(uri, "w")?.use {
                FileOutputStream(it.fileDescriptor).use { fileOutputStream ->
                    fileOutputStream.write(jsonString.toByteArray())
                }
            }
        } catch (
            exception: FileNotFoundException,
        ) {
            exception.printStackTrace()
        } catch (
            exception: IOException,
        ) {
            exception.printStackTrace()
        }
    }
}

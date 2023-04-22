package com.makeappssimple.abhimanyu.financemanager.android.core.database.util

import android.content.Context
import android.net.Uri
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.database.amount.model.AmountJsonAdapter
import com.makeappssimple.abhimanyu.financemanager.android.core.database.databasebackupdata.model.DatabaseBackupData
import com.makeappssimple.abhimanyu.financemanager.android.core.database.initialdatabasedata.model.InitialDatabaseData
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.Charset

private val moshi = Moshi.Builder()
    .add(AmountJsonAdapter())
    .build()
private val initialDatabaseDataJsonAdapter: JsonAdapter<InitialDatabaseData> =
    moshi.adapter(InitialDatabaseData::class.java)
private val databaseBackupDataJsonAdapter: JsonAdapter<DatabaseBackupData> =
    moshi.adapter(DatabaseBackupData::class.java)

interface JsonUtil {
    fun readDatabaseBackupDataFromFile(
        uri: Uri,
    ): DatabaseBackupData?

    fun writeDatabaseBackupDataToFile(
        uri: Uri,
        databaseBackupData: DatabaseBackupData,
    )
}

class JsonUtilImpl(
    private val context: Context,
) : JsonUtil {
    override fun readDatabaseBackupDataFromFile(
        uri: Uri,
    ): DatabaseBackupData? {
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
        return databaseBackupDataJsonAdapter.fromJson(stringBuilder.toString())
    }

    override fun writeDatabaseBackupDataToFile(
        uri: Uri,
        databaseBackupData: DatabaseBackupData,
    ) {
        val jsonString = databaseBackupDataJsonAdapter.toJson(databaseBackupData)
        writeJsonToFile(
            uri = uri,
            jsonString = jsonString,
        )
    }

    private fun writeJsonToFile(
        uri: Uri,
        jsonString: String,
    ) {
        val contentResolver = context.contentResolver
        try {
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

fun readInitialDataFromAssets(
    context: Context,
): InitialDatabaseData? {
    val jsonString = readJsonFileFromAssets(
        context = context,
        fileName = "database/initial_data.json",
    ) ?: return null
    return initialDatabaseDataJsonAdapter.fromJson(jsonString)
}

@Suppress("SameParameterValue")
private fun readJsonFileFromAssets(
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

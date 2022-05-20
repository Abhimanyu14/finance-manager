@file:Suppress("SameParameterValue")

package com.makeappssimple.abhimanyu.financemanager.utils

import com.makeappssimple.abhimanyu.financemanager.adapters.AmountJsonAdapter
import com.makeappssimple.abhimanyu.financemanager.entities.initialdatabasedata.InitialDatabaseInputDataEntity
import com.makeappssimple.abhimanyu.financemanager.entities.initialdatabasedata.InitialDatabaseOutputDataEntity
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

private val moshi = Moshi.Builder()
    .add(AmountJsonAdapter())
    .add(KotlinJsonAdapterFactory())
    .build()
private val initialDatabaseInputDataJsonAdapter: JsonAdapter<InitialDatabaseInputDataEntity> =
    moshi.adapter(InitialDatabaseInputDataEntity::class.java)
private val initialDatabaseOutputDataJsonAdapter: JsonAdapter<InitialDatabaseOutputDataEntity> =
    moshi.adapter(InitialDatabaseOutputDataEntity::class.java)

private const val projectPath = "/Users/abhimanyu/Documents/projects/finance-manager/codebase/utils/json_parser_util"
private const val rootDirectoryPath = "$projectPath/src/main/kotlin/com/makeappssimple/abhimanyu/financemanager"
private const val inputFileName = "$rootDirectoryPath/data/initial_data.json"
private const val outputFileName = "$rootDirectoryPath/data/initial_data_2.json"

fun readInitialDatabaseDataFile(): InitialDatabaseInputDataEntity? {
    val jsonString = readJsonFromFile(
        fileName = inputFileName,
    ) ?: return null
    return initialDatabaseInputDataJsonAdapter.fromJson(jsonString)
}

fun writeToInitialDatabaseDataFile(
    initialDatabaseOutputDataEntity: InitialDatabaseOutputDataEntity,
) {
    val jsonString = initialDatabaseOutputDataJsonAdapter.toJson(initialDatabaseOutputDataEntity)
    writeJsonToFile(
        fileName = outputFileName,
        jsonString = jsonString,
    )
}

private fun writeJsonToFile(
    fileName: String,
    jsonString: String,
) {
    try {
        val outputStream = File(fileName).outputStream()
        outputStream.write(jsonString.toByteArray())
        outputStream.close()
    } catch (fileNotFoundException: FileNotFoundException) {
        fileNotFoundException.printStackTrace()
    } catch (ioException: IOException) {
        ioException.printStackTrace()
    }
}

private fun readJsonFromFile(
    fileName: String,
): String? {
    val json = try {
        val inputStream: InputStream = File(fileName).inputStream()
        val size: Int = inputStream.available()
        val byteArray = ByteArray(size)
        inputStream.read(byteArray)
        inputStream.close()
        String(
            bytes = byteArray,
            charset = Charset.forName("UTF-8"),
        )
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        null
    }
    return json
}

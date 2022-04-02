package com.makeappssimple.abhimanyu.financemanager.android.utils

import android.content.Context
import com.makeappssimple.abhimanyu.financemanager.android.entities.amount.AmountJsonAdapter
import com.makeappssimple.abhimanyu.financemanager.android.entities.initialdatabasedata.InitialDatabaseData
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

private val moshi = Moshi.Builder()
    .add(AmountJsonAdapter())
    .add(KotlinJsonAdapterFactory())
    .build()
private val initialDatabaseDataJsonAdapter: JsonAdapter<InitialDatabaseData> =
    moshi.adapter(InitialDatabaseData::class.java)

fun readInitialDataFromAssets(
    context: Context,
): InitialDatabaseData? {
    val jsonString = readJsonFileFromAssets(
        context = context,
        fileName = "database/initial_data.json",
    ) ?: return null
    return initialDatabaseDataJsonAdapter.fromJson(jsonString)
}

fun readJsonFileFromAssets(
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
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        null
    }
    return json
}

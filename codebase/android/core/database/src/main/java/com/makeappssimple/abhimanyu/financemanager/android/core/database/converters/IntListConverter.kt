package com.makeappssimple.abhimanyu.financemanager.android.core.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull

class IntListConverter {

    @TypeConverter
    fun stringToIntList(
        value: String?,
    ): List<Int>? {
        if (value.isNullOrBlank()) {
            return null
        }
        val gson = Gson()
        val listType = object : TypeToken<List<Int>>() {}.type
        return try {
            gson.fromJson(
                value,
                listType,
            )
        } catch (
            exception: JsonSyntaxException,
        ) {
            exception.printStackTrace()
            null
        }
    }

    @TypeConverter
    fun intListToString(
        intList: List<Int>?,
    ): String {
        if (intList.isNull()) {
            return ""
        }
        val gson = Gson()
        return gson.toJson(
            intList,
        )
    }
}

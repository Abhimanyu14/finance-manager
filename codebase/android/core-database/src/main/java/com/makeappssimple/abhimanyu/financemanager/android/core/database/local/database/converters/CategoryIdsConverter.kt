package com.makeappssimple.abhimanyu.financemanager.android.core.database.local.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.makeappssimple.abhimanyu.financemanager.android.core.database.utils.extensions.isNull

class CategoryIdsConverter {

    @TypeConverter
    fun stringToCategoryIds(
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
            JsonSyntaxException: JsonSyntaxException,
        ) {
            null
        }
    }

    @TypeConverter
    fun categoryIdsToString(
        categoryIds: List<Int>?,
    ): String {
        if (categoryIds.isNull()) {
            return ""
        }
        val gson = Gson()
        return gson.toJson(
            categoryIds,
        )
    }
}

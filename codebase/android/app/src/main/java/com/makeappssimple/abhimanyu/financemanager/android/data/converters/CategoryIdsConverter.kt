package com.makeappssimple.abhimanyu.financemanager.android.data.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CategoryIdsConverter {

    @TypeConverter
    fun stringToCategories(
        value: String?,
    ): List<Int>? {
        if (value.isNullOrBlank()) {
            return null
        }
        val gson = Gson()
        val listType = object : TypeToken<List<Int>>() {}.type
        return gson.fromJson(
            value,
            listType,
        )
    }

    @TypeConverter
    fun categoryToString(
        categoryIds: List<Int>?,
    ): String {
        if (categoryIds == null) {
            return ""
        }
        val gson = Gson()
        return gson.toJson(
            categoryIds,
        )
    }
}

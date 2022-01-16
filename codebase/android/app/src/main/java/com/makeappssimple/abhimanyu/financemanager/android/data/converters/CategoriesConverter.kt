package com.makeappssimple.abhimanyu.financemanager.android.data.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.makeappssimple.abhimanyu.financemanager.android.models.Category

class CategoriesConverter {

    @TypeConverter
    fun stringToCategories(
        value: String?,
    ): List<Category>? {
        if (value.isNullOrBlank()) {
            return null
        }
        val gson = Gson()
        val listType = object : TypeToken<List<Category>>() {}.type
        return gson.fromJson(
            value,
            listType,
        )
    }

    @TypeConverter
    fun categoryToString(
        categories: List<Category>?,
    ): String {
        if (categories == null) {
            return ""
        }
        val gson = Gson()
        return gson.toJson(
            categories,
        )
    }
}

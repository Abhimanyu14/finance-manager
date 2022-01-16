package com.makeappssimple.abhimanyu.financemanager.android.data.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.makeappssimple.abhimanyu.financemanager.android.models.Category

class CategoryConverter {

    @TypeConverter
    fun stringToCategory(
        value: String?,
    ): Category? {
        if (value.isNullOrBlank()) {
            return null
        }
        val gson = Gson()
        return gson.fromJson(
            value,
            Category::class.java,
        )
    }

    @TypeConverter
    fun categoryToString(
        category: Category?,
    ): String {
        if (category == null) {
            return ""
        }
        val gson = Gson()
        return gson.toJson(
            category,
        )
    }
}

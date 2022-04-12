package com.makeappssimple.abhimanyu.financemanager.android.data.local.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.makeappssimple.abhimanyu.financemanager.android.entities.category.Category

class CategoryConverter {

    @TypeConverter
    fun stringToCategory(
        value: String?,
    ): Category? {
        if (value.isNullOrBlank()) {
            return null
        }
        val gson = Gson()
        return try {
            gson.fromJson(
                value,
                Category::class.java,
            )
        } catch (
            JsonSyntaxException: JsonSyntaxException,
        ) {
            null
        }
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

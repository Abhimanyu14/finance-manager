package com.makeappssimple.abhimanyu.financemanager.android.core.database.converters

import androidx.room.TypeConverter
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.Category
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class CategoryConverter {
    @TypeConverter
    fun stringToCategory(
        value: String?,
    ): Category? {
        if (value.isNullOrBlank()) {
            return null
        }
        return try {
            Json.decodeFromString<Category>(
                string = value,
            )
        } catch (
            exception: Exception,
        ) {
            exception.printStackTrace()
            null
        }
    }

    @TypeConverter
    fun categoryToString(
        category: Category?,
    ): String {
        if (category.isNull()) {
            return ""
        }
        return try {
            Json.encodeToString(
                value = category,
            )
        } catch (
            exception: Exception,
        ) {
            exception.printStackTrace()
            ""
        }
    }
}

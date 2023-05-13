package com.makeappssimple.abhimanyu.financemanager.android.core.database.converters

import androidx.room.TypeConverter
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.CategoryEntity
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class CategoryConverter {
    @TypeConverter
    fun stringToCategory(
        value: String?,
    ): CategoryEntity? {
        if (value.isNullOrBlank()) {
            return null
        }
        return try {
            Json.decodeFromString<CategoryEntity>(
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
        categoryEntity: CategoryEntity?,
    ): String {
        if (categoryEntity.isNull()) {
            return ""
        }
        return try {
            Json.encodeToString(
                value = categoryEntity,
            )
        } catch (
            exception: Exception,
        ) {
            exception.printStackTrace()
            ""
        }
    }
}

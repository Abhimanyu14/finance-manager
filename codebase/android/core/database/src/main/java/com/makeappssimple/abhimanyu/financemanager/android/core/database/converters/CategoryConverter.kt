package com.makeappssimple.abhimanyu.financemanager.android.core.database.converters

import androidx.room.TypeConverter
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.CategoryEntity
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

public class CategoryConverter {
    @TypeConverter
    public fun stringToCategory(
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
            serializationException: SerializationException,
        ) {
            serializationException.printStackTrace()
            null
        } catch (
            illegalArgumentException: IllegalArgumentException,
        ) {
            illegalArgumentException.printStackTrace()
            null
        }
    }

    @TypeConverter
    public fun categoryToString(
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
            serializationException: SerializationException,
        ) {
            serializationException.printStackTrace()
            ""
        } catch (
            illegalArgumentException: IllegalArgumentException,
        ) {
            illegalArgumentException.printStackTrace()
            ""
        }
    }
}

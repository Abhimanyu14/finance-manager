package com.makeappssimple.abhimanyu.financemanager.android.core.database.converters

import androidx.room.TypeConverter
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

public class IntListConverter {
    @TypeConverter
    public fun stringToIntList(
        value: String?,
    ): List<Int>? {
        if (value.isNullOrBlank()) {
            return null
        }
        return try {
            Json.decodeFromString<List<Int>>(
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
    public fun intListToString(
        intList: List<Int>?,
    ): String {
        if (intList.isNull()) {
            return ""
        }
        return try {
            Json.encodeToString(
                value = intList,
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

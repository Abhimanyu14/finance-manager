package com.makeappssimple.abhimanyu.financemanager.android.data.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.makeappssimple.abhimanyu.financemanager.android.models.Timestamp

class TimestampConverter {

    @TypeConverter
    fun stringToTimestamp(
        value: String?,
    ): Timestamp? {
        if (value.isNullOrBlank()) {
            return null
        }
        val gson = Gson()
        return gson.fromJson(
            value,
            Timestamp::class.java,
        )
    }

    @TypeConverter
    fun timestampToString(
        timestamp: Timestamp?,
    ): String {
        if (timestamp == null) {
            return ""
        }
        val gson = Gson()
        return gson.toJson(
            timestamp,
        )
    }
}

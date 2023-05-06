package com.makeappssimple.abhimanyu.financemanager.android.core.database.converters

import androidx.room.TypeConverter
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.Amount
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class AmountConverter {
    @TypeConverter
    fun stringToAmount(
        value: String?,
    ): Amount? {
        if (value.isNullOrBlank()) {
            return null
        }
        return try {
            Json.decodeFromString<Amount>(
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
    fun amountToString(
        amount: Amount?,
    ): String {
        if (amount.isNull()) {
            return ""
        }
        return try {
            Json.encodeToString(
                value = amount,
            )
        } catch (
            exception: Exception,
        ) {
            exception.printStackTrace()
            ""
        }
    }
}

package com.makeappssimple.abhimanyu.financemanager.android.cre.database.converters

import androidx.room.TypeConverter
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.cre.database.model.AmountEntity
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

public class AmountConverter {
    @TypeConverter
    public fun stringToAmount(
        value: String?,
    ): AmountEntity? {
        if (value.isNullOrBlank()) {
            return null
        }
        return try {
            Json.decodeFromString<AmountEntity>(
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
    public fun amountToString(
        amount: AmountEntity?,
    ): String {
        if (amount.isNull()) {
            return ""
        }
        return try {
            Json.encodeToString(
                value = amount,
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

package com.makeappssimple.abhimanyu.financemanager.android.data.local.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.makeappssimple.abhimanyu.financemanager.android.entities.amount.Amount

class AmountConverter {

    @TypeConverter
    fun stringToAmount(
        value: String?,
    ): Amount? {
        if (value.isNullOrBlank()) {
            return null
        }
        val gson = Gson()
        return try {
            gson.fromJson(
                value,
                Amount::class.java,
            )
        } catch (
            JsonSyntaxException: JsonSyntaxException,
        ) {
            null
        }
    }

    @TypeConverter
    fun amountToString(
        amount: Amount?,
    ): String {
        if (amount == null) {
            return ""
        }
        val gson = Gson()
        return gson.toJson(
            amount,
        )
    }
}

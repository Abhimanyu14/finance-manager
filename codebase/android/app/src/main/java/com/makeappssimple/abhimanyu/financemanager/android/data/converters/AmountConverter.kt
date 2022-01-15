package com.makeappssimple.abhimanyu.financemanager.android.data.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.makeappssimple.abhimanyu.financemanager.android.models.Amount

class AmountConverter {

    @TypeConverter
    fun stringToAmount(
        value: String,
    ): Amount {
        val gson = Gson()
        return gson.fromJson(
            value,
            Amount::class.java,
        )
    }

    @TypeConverter
    fun amountToString(
        amount: Amount,
    ): String {
        val gson = Gson()
        return gson.toJson(
            amount,
        )
    }
}

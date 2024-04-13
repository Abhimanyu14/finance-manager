package com.makeappssimple.abhimanyu.financemanager.android.core.database.serializer

import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.CurrencyCodeConstants
import com.makeappssimple.abhimanyu.financemanager.android.core.model.serializer.CurrencySerializer
import kotlinx.serialization.json.Json
import org.junit.Assert
import org.junit.Test
import java.util.Currency

public class CurrencySerializerTest {
    @Test
    public fun deserialize_defaultTest() {
        val result = Json.decodeFromString(
            deserializer = CurrencySerializer,
            string = testJson,
        )
        Assert.assertEquals(
            Currency.getInstance(CurrencyCodeConstants.INR),
            result,
        )
    }

    @Test
    public fun serialize_defaultTest() {
        val result = Json.encodeToString(
            serializer = CurrencySerializer,
            value = Currency.getInstance(CurrencyCodeConstants.INR),
        )
        Assert.assertEquals(
            testJson,
            result,
        )
    }

    public companion object {
        private const val testJson = "\"${CurrencyCodeConstants.INR}\""
    }
}

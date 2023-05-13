package com.makeappssimple.abhimanyu.financemanager.android.core.model.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind.STRING
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.Currency

object CurrencySerializer : KSerializer<Currency> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = "Currency",
        kind = STRING
    )

    override fun deserialize(
        decoder: Decoder,
    ): Currency {
        return Currency.getInstance(decoder.decodeString())
    }

    override fun serialize(
        encoder: Encoder,
        value: Currency,
    ) {
        val encodedValue = value.currencyCode
        encoder.encodeString(
            value = encodedValue,
        )
    }
}

package com.makeappssimple.abhimanyu.financemanager.android.core.ui.common

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.formattedCurrencyValue

/**
 * Format long to comma separated string.
 *
 * Example: 1234567899 => 123,456,789,9
 * */
class AmountCommaVisualTransformation : VisualTransformation {
    override fun filter(
        text: AnnotatedString,
    ): TransformedText {
        return TransformedText(
            text = AnnotatedString(
                formattedCurrencyValue(
                    value = text.text.toLongOrNull() ?: 0,
                ),
            ),
            offsetMapping = object : OffsetMapping {
                override fun originalToTransformed(
                    offset: Int,
                ): Int {
                    return formattedCurrencyValue(
                        value = text.text.toLongOrNull() ?: 0,
                    ).length
                }

                override fun transformedToOriginal(
                    offset: Int,
                ): Int {
                    return text.length
                }
            }
        )
    }
}

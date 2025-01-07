package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.extensions

import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions.isNull

public fun TextFieldValue?.orEmpty(): TextFieldValue {
    return if (this.isNull()) {
        TextFieldValue()
    } else {
        this
    }
}

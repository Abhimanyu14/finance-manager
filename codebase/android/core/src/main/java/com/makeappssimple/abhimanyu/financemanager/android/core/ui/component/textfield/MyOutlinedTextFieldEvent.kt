package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfield

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue

@Immutable
public sealed class MyOutlinedTextFieldEvent {
    public data object OnClickTrailingIcon : MyOutlinedTextFieldEvent()
    public data class OnValueChange(
        val updatedValue: TextFieldValue,
    ) : MyOutlinedTextFieldEvent()
}

package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.textfield

import androidx.annotation.StringRes
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import com.makeappssimple.abhimanyu.financemanager.android.cre.designsystem.typealiases.NullableComposableContent

@Immutable
public data class MyOutlinedTextFieldData(
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    @StringRes val labelTextStringResourceId: Int,
    @StringRes val trailingIconContentDescriptionTextStringResourceId: Int,
    val keyboardActions: KeyboardActions = KeyboardActions(),
    val keyboardOptions: KeyboardOptions = KeyboardOptions(),
    val textFieldValue: TextFieldValue = TextFieldValue(),
    val visualTransformation: VisualTransformation = VisualTransformation.None,
    val supportingText: NullableComposableContent = null,
)

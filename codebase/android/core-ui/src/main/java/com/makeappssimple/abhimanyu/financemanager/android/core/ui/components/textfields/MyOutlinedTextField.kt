package com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.textfields

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNullOrBlank
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.buttons.MyIconButton

@Composable
fun MyOutlinedTextField(
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    value: String,
    @StringRes labelTextStringResourceId: Int,
    @StringRes trailingIconContentDescriptionTextStringResourceId: Int,
    keyboardActions: KeyboardActions,
    keyboardOptions: KeyboardOptions,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onClickTrailingIcon: () -> Unit,
    onValueChange: (updatedValue: String) -> Unit,
    supportingText: @Composable (() -> Unit)? = null,
) {
    OutlinedTextField(
        value = value,
        label = {
            MyOutlinedTextFieldLabelText(
                textStringResourceId = labelTextStringResourceId,
            )
        },
        trailingIcon = {
            AnimatedVisibility(
                visible = value.isNotNullOrBlank(),
                enter = fadeIn(),
                exit = fadeOut(),
            ) {
                MyIconButton(
                    onClickLabel = stringResource(
                        id = trailingIconContentDescriptionTextStringResourceId,
                    ),
                    onClick = onClickTrailingIcon,
                    modifier = Modifier
                        .padding(
                            end = 4.dp,
                        ),
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Clear,
                        tint = MaterialTheme.colorScheme.onBackground,
                        contentDescription = stringResource(
                            id = trailingIconContentDescriptionTextStringResourceId,
                        ),
                    )
                }
            }
        },
        onValueChange = {
            onValueChange(it)
        },
        supportingText = supportingText,
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        singleLine = true,
        modifier = modifier,
    )
}

package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfield

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNullOrBlank
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.button.MyIconButton
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.shimmer.shimmer

@Immutable
data class MyOutlinedTextFieldData(
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    @StringRes val labelTextStringResourceId: Int,
    @StringRes val trailingIconContentDescriptionTextStringResourceId: Int,
    val keyboardActions: KeyboardActions = KeyboardActions(),
    val keyboardOptions: KeyboardOptions = KeyboardOptions(),
    val textFieldValue: TextFieldValue = TextFieldValue(),
    val visualTransformation: VisualTransformation = VisualTransformation.None,
    val supportingText: @Composable (() -> Unit)? = null,
)

@Immutable
data class MyOutlinedTextFieldEvents(
    val onClickTrailingIcon: () -> Unit = {},
    val onValueChange: (updatedValue: TextFieldValue) -> Unit = {},
)

@Composable
fun MyOutlinedTextField(
    modifier: Modifier = Modifier,
    data: MyOutlinedTextFieldData,
    events: MyOutlinedTextFieldEvents = MyOutlinedTextFieldEvents(),
) {
    if (data.isLoading) {
        MyOutlinedTextFieldLoadingUI(
            modifier = modifier,
        )
    } else {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            OutlinedTextField(
                value = data.textFieldValue,
                label = {
                    MyOutlinedTextFieldLabelText(
                        textStringResourceId = data.labelTextStringResourceId,
                    )
                },
                trailingIcon = {
                    AnimatedVisibility(
                        visible = data.textFieldValue.text.isNotNullOrBlank(),
                        enter = fadeIn(),
                        exit = fadeOut(),
                    ) {
                        MyIconButton(
                            onClickLabel = stringResource(
                                id = data.trailingIconContentDescriptionTextStringResourceId,
                            ),
                            onClick = events.onClickTrailingIcon,
                            modifier = Modifier
                                .padding(
                                    end = 4.dp,
                                ),
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Clear,
                                tint = MaterialTheme.colorScheme.onBackground,
                                contentDescription = stringResource(
                                    id = data.trailingIconContentDescriptionTextStringResourceId,
                                ),
                            )
                        }
                    }
                },
                onValueChange = events.onValueChange,
                supportingText = data.supportingText,
                isError = data.isError,
                visualTransformation = data.visualTransformation,
                keyboardActions = data.keyboardActions,
                keyboardOptions = data.keyboardOptions,
                singleLine = true,
                modifier = modifier,
            )
        }
    }
}

@Composable
private fun MyOutlinedTextFieldLoadingUI(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(
                height = 56.dp,
            )
            .clip(
                shape = RoundedCornerShape(
                    size = 8.dp,
                ),
            )
            .shimmer(),
    )
}

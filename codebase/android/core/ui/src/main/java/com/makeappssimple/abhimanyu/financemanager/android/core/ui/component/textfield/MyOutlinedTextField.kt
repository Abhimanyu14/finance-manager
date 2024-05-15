package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfield

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNullOrBlank
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.button.MyIconButton
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.icons.MyIcons
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.shimmer.shimmer

@Composable
public fun MyOutlinedTextField(
    modifier: Modifier = Modifier,
    data: MyOutlinedTextFieldData,
    handleEvent: (event: MyOutlinedTextFieldEvent) -> Unit = {},
) {
    if (data.isLoading) {
        MyOutlinedTextFieldLoadingUI(
            modifier = modifier,
        )
    } else {
        // TODO(Abhi): Check if this box is required
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier,
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
                            tint = MaterialTheme.colorScheme.onBackground,
                            imageVector = MyIcons.Clear,
                            contentDescriptionStringResourceId = data.trailingIconContentDescriptionTextStringResourceId,
                            onClick = {
                                handleEvent(MyOutlinedTextFieldEvent.OnClickTrailingIcon)
                            },
                            modifier = Modifier
                                .padding(
                                    end = 4.dp,
                                ),
                        )
                    }
                },
                onValueChange = {
                    handleEvent(
                        MyOutlinedTextFieldEvent.OnValueChange(
                            updatedValue = it,
                        )
                    )
                },
                supportingText = data.supportingText,
                isError = data.isError,
                visualTransformation = data.visualTransformation,
                keyboardActions = data.keyboardActions,
                keyboardOptions = data.keyboardOptions,
                singleLine = true,
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

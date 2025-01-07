package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.textfield

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.cre.designsystem.extensions.conditionalClickable
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.extensions.shimmer.shimmer

@Composable
public fun MyReadOnlyTextField(
    modifier: Modifier = Modifier,
    data: MyReadOnlyTextFieldData,
    handleEvent: (event: MyReadOnlyTextFieldEvent) -> Unit = {},
) {
    if (data.isLoading) {
        MyReadOnlyTextFieldLoadingUI(
            modifier = modifier,
        )
    } else {
        Box(
            modifier = modifier,
        ) {
            OutlinedTextField(
                value = data.value,
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth(),
                label = {
                    MyOutlinedTextFieldLabelText(
                        textStringResourceId = data.labelTextStringResourceId,
                    )
                },
            )
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .alpha(
                        alpha = 0F,
                    )
                    .conditionalClickable(
                        onClick = {
                            handleEvent(MyReadOnlyTextFieldEvent.OnClick)
                        },
                    ),
            )
        }
    }
}

@Composable
private fun MyReadOnlyTextFieldLoadingUI(
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

package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.NavigationBarsAndImeSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.minimumBottomSheetHeight

@Composable
public fun MyConfirmationBottomSheet(
    modifier: Modifier = Modifier,
    data: MyConfirmationBottomSheetData,
    handleEvent: (event: MyConfirmationBottomSheetEvent) -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = 16.dp,
            )
            .defaultMinSize(
                minHeight = minimumBottomSheetHeight,
            ),
    ) {
        MyText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    all = 16.dp,
                ),
            text = data.title,
            style = MaterialTheme.typography.headlineLarge
                .copy(
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                ),
        )
        MyText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    all = 16.dp,
                ),
            text = data.message,
            style = MaterialTheme.typography.bodyMedium
                .copy(
                    color = MaterialTheme.colorScheme.onBackground,
                ),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OutlinedButton(
                onClick = {
                    handleEvent(MyConfirmationBottomSheetEvent.OnNegativeButtonClick)
                },
                modifier = Modifier
                    .padding(
                        all = 16.dp,
                    )
                    .weight(
                        weight = 1F,
                    ),
            ) {
                MyText(
                    text = data.negativeButtonText,
                    style = MaterialTheme.typography.labelLarge,
                )
            }
            Button(
                onClick = {
                    handleEvent(MyConfirmationBottomSheetEvent.OnPositiveButtonClick)
                },
                modifier = Modifier
                    .padding(
                        all = 16.dp,
                    )
                    .weight(
                        weight = 1F,
                    ),
            ) {
                MyText(
                    text = data.positiveButtonText,
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }
        NavigationBarsAndImeSpacer()
    }
}

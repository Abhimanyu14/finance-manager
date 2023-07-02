package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottom_sheet

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
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.NavigationBarSpacer

@Composable
fun MyConfirmationBottomSheetUI(
    modifier: Modifier = Modifier,
    title: String,
    message: String,
    positiveButtonText: String,
    negativeButtonText: String,
    onPositiveButtonClick: () -> Unit,
    onNegativeButtonClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = 16.dp,
            )
            .defaultMinSize(
                minHeight = 24.dp,
            ),
    ) {
        MyText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    all = 16.dp,
                ),
            text = title,
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
            text = message,
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
                    onNegativeButtonClick()
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
                    text = negativeButtonText,
                    style = MaterialTheme.typography.labelLarge,
                )
            }
            Button(
                onClick = {
                    onPositiveButtonClick()
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
                    text = positiveButtonText,
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }
        NavigationBarSpacer()
    }
}

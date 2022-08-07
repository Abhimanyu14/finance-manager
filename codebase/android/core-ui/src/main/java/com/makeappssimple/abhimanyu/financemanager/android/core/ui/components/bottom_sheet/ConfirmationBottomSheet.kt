package com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.bottom_sheet

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

data class ConfirmationBottomSheetData(
    val title: String,
    val message: String,
    val positiveButtonText: String,
    val negativeButtonText: String,
    val onPositiveButtonClick: () -> Unit,
    val onNegativeButtonClick: () -> Unit,
)

@Composable
fun ConfirmationBottomSheet(
    data: ConfirmationBottomSheetData,
) {
    Column(
        modifier = Modifier
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
                    data.onNegativeButtonClick()
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
                    data.onPositiveButtonClick()
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
    }
}

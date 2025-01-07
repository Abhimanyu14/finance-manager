package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.actionbutton

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.cre.chart.composepie.legend.Dot
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.extensions.shimmer.shimmer

private object ActionButtonConstants {
    val loadingUISize = 40.dp
    val loadingUIPadding = 4.dp
}

@Composable
public fun ActionButton(
    modifier: Modifier = Modifier,
    data: ActionButtonData,
    handleEvent: (event: ActionButtonEvent) -> Unit = {},
) {
    if (data.isLoading) {
        ActionButtonLoadingUI(
            modifier = modifier,
        )
    } else {
        ActionButtonUI(
            modifier = modifier,
            data = data,
            handleEvent = handleEvent,
        )
    }
}

@Composable
private fun ActionButtonUI(
    modifier: Modifier = Modifier,
    data: ActionButtonData,
    handleEvent: (event: ActionButtonEvent) -> Unit,
) {
    ElevatedCard(
        onClick = {
            handleEvent(ActionButtonEvent.OnClick)
        },
        modifier = modifier,
    ) {
        Box {
            Icon(
                imageVector = data.imageVector,
                contentDescription = stringResource(
                    id = data.contentDescriptionStringResourceId,
                ),
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                    )
                    .padding(
                        all = 8.dp,
                    ),
            )
            if (data.isIndicatorVisible) {
                Dot(
                    modifier = Modifier
                        .align(
                            alignment = Alignment.TopEnd,
                        )
                        .padding(
                            all = 8.dp,
                        ),
                    color = MaterialTheme.colorScheme.error,
                )
            }
        }
    }
}

@Composable
private fun ActionButtonLoadingUI(
    modifier: Modifier = Modifier,
) {
    ElevatedCard(
        modifier = modifier
            .padding(
                all = ActionButtonConstants.loadingUIPadding,
            ),
    ) {
        Box(
            modifier = modifier
                .size(
                    size = DpSize(
                        width = ActionButtonConstants.loadingUISize,
                        height = ActionButtonConstants.loadingUISize,
                    ),
                )
                .shimmer(),
        )
    }
}

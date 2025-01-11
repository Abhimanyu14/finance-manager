package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.home_recent_transactions

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.R
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.extensions.conditionalClickable

@Composable
public fun HomeRecentTransactions(
    modifier: Modifier = Modifier,
    data: HomeRecentTransactionsData,
    handleEvent: (event: HomeRecentTransactionsEvent) -> Unit = {},
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = 8.dp,
                end = 8.dp,
                top = 16.dp,
                bottom = 8.dp,
            )
            .clip(
                shape = CircleShape,
            )
            .conditionalClickable(
                onClickLabel = stringResource(
                    id = R.string.screen_home_view_all_transactions,
                ),
                onClick = {
                    handleEvent(HomeRecentTransactionsEvent.OnClick)
                },
            )
            .padding(
                top = 12.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 12.dp,
            ),
    ) {
        MyText(
            modifier = Modifier
                .weight(
                    weight = 1F,
                ),
            textStringResourceId = R.string.screen_home_recent_transactions,
            style = MaterialTheme.typography.headlineLarge
                .copy(
                    color = MaterialTheme.colorScheme.onBackground,
                ),
        )
        if (data.isTrailingTextVisible) {
            MyText(
                modifier = Modifier,
                textStringResourceId = R.string.screen_home_view_all_transactions,
                style = MaterialTheme.typography.headlineMedium
                    .copy(
                        color = MaterialTheme.colorScheme.primary,
                    ),
            )
        }
    }
}

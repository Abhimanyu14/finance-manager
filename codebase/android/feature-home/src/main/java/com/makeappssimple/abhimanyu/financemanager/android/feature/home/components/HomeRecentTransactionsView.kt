package com.makeappssimple.abhimanyu.financemanager.android.feature.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.R

@Composable
internal fun HomeRecentTransactionsView(
    onClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
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
            .clickable(
                onClickLabel = stringResource(
                    id = R.string.screen_home_view_all_transactions,
                ),
                onClick = onClick,
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
        Icon(
            imageVector = Icons.Rounded.ChevronRight,
            tint = MaterialTheme.colorScheme.onBackground,
            contentDescription = stringResource(
                id = R.string.screen_home_view_all_transactions,
            ),
        )
    }
}

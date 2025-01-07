package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.listitem.accounts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.cre.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.extensions.shimmer.shimmer

private object AccountsListItemHeaderConstants {
    val loadingUIHeight = 24.dp
    val loadingUIWidth = 160.dp
    val loadingUIHorizontalPadding = 16.dp
    val loadingUITopPadding = 2.dp
    val loadingUIBottomPadding = 2.dp
    val loadingUIVerticalSpacing = 20.dp
}

@Composable
public fun AccountsListItemHeader(
    modifier: Modifier = Modifier,
    data: AccountsListItemHeaderData,
) {
    AccountsListItemHeaderUI(
        modifier = modifier,
        data = data,
    )
}

@Composable
private fun AccountsListItemHeaderUI(
    modifier: Modifier,
    data: AccountsListItemHeaderData,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clip(
                shape = MaterialTheme.shapes.large,
            )
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp,
                bottom = 0.dp,
            ),
    ) {
        MyText(
            modifier = Modifier
                .padding(
                    end = 16.dp,
                ),
            text = data.name,
            style = MaterialTheme.typography.headlineMedium
                .copy(
                    color = MaterialTheme.colorScheme.onBackground,
                ),
        )
        Spacer(
            modifier = Modifier
                .weight(
                    weight = 1F,
                ),
        )
        data.balance?.let {
            MyText(
                text = data.balance,
                style = MaterialTheme.typography.headlineMedium
                    .copy(
                        color = MaterialTheme.colorScheme.onBackground,
                    ),
            )
        }
    }
}

@Composable
public fun AccountsListItemHeaderLoadingUI(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .requiredSize(
                width = AccountsListItemHeaderConstants.loadingUIWidth,
                height = AccountsListItemHeaderConstants.loadingUIHeight,
            )
            .padding(
                start = AccountsListItemHeaderConstants.loadingUIHorizontalPadding,
                end = AccountsListItemHeaderConstants.loadingUIHorizontalPadding,
                top = AccountsListItemHeaderConstants.loadingUITopPadding,
                bottom = AccountsListItemHeaderConstants.loadingUIBottomPadding,
            )
            .clip(
                shape = MaterialTheme.shapes.small,
            )
            .shimmer(),
    )
}

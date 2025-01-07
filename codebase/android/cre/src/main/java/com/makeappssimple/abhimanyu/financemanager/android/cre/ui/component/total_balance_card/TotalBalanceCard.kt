package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.total_balance_card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.cre.R
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.constants.TestTags.COMPONENT_TOTAL_BALANCE_CARD
import com.makeappssimple.abhimanyu.financemanager.android.cre.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.cre.designsystem.extensions.conditionalClickable
import com.makeappssimple.abhimanyu.financemanager.android.cre.designsystem.icons.MyIcons
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.chip.ChipUI
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.chip.ChipUIEvent
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.extensions.matchrowsize.matchRowSize
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.extensions.shimmer.shimmer

private object TotalBalanceCardConstants {
    val loadingUIHeight = 96.dp
    val loadingUIHorizontalPadding = 32.dp
    val loadingUIVerticalPadding = 16.dp
}

/**
 * This is coupled with [Amount].
 */
@Composable
public fun TotalBalanceCard(
    modifier: Modifier = Modifier,
    data: TotalBalanceCardData,
    handleEvent: (event: TotalBalanceCardEvent) -> Unit = {},
) {
    val modifierWithTestTag = modifier
        .testTag(
            tag = COMPONENT_TOTAL_BALANCE_CARD,
        )
    if (data.isLoading) {
        TotalBalanceCardLoadingUI(
            modifier = modifierWithTestTag,
        )
    } else {
        TotalBalanceCardUI(
            modifier = modifierWithTestTag,
            data = data,
            handleEvent = handleEvent,
        )
    }
}

@Composable
private fun TotalBalanceCardUI(
    modifier: Modifier = Modifier,
    data: TotalBalanceCardData,
    handleEvent: (event: TotalBalanceCardEvent) -> Unit = {},
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 32.dp,
                vertical = 16.dp,
            )
            .clip(
                MaterialTheme.shapes.medium,
            )
            .background(
                color = MaterialTheme.colorScheme.tertiary,
            )
            .conditionalClickable(
                onClick = if (data.isClickable) {
                    {
                        handleEvent(TotalBalanceCardEvent.OnClick)
                    }
                } else {
                    null
                },
            )
            .padding(
                all = 16.dp,
            ),
    ) {
        if (data.isBalanceVisible) {
            MyText(
                modifier = Modifier
                    .fillMaxWidth(),
                textStringResourceId = R.string.total_balance_card_title,
                style = MaterialTheme.typography.displaySmall
                    .copy(
                        color = MaterialTheme.colorScheme.onTertiary,
                        textAlign = TextAlign.Center,
                    ),
            )
            MyText(
                modifier = Modifier
                    .fillMaxWidth(),
                text = Amount(
                    value = data.totalBalanceAmount,
                ).toString(),
                style = MaterialTheme.typography.displayLarge
                    .copy(
                        color = MaterialTheme.colorScheme.onTertiary,
                        textAlign = TextAlign.Center,
                    ),
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    modifier = Modifier
                        .padding(2.dp)
                        .matchRowSize(),
                    imageVector = MyIcons.Lock,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onTertiary,
                )
                MyText(
                    text = Amount(
                        value = data.totalMinimumBalanceAmount,
                    ).toString(),
                    style = MaterialTheme.typography.bodySmall
                        .copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onTertiary,
                            textAlign = TextAlign.End,
                        ),
                )
            }
        } else {
            ChipUI(
                modifier = Modifier,
                data = ChipUIData(
                    borderColor = MaterialTheme.colorScheme.onTertiary,
                    textColor = MaterialTheme.colorScheme.onTertiary,
                    text = stringResource(
                        id = R.string.total_balance_card_view_balance,
                    ),
                ),
                handleEvent = { event ->
                    when (event) {
                        is ChipUIEvent.OnClick -> {
                            handleEvent(TotalBalanceCardEvent.OnViewBalanceClick)
                        }
                    }
                },
            )
        }
    }
}

@Composable
private fun TotalBalanceCardLoadingUI(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(
                height = TotalBalanceCardConstants.loadingUIHeight,
            )
            .padding(
                horizontal = TotalBalanceCardConstants.loadingUIHorizontalPadding,
                vertical = TotalBalanceCardConstants.loadingUIVerticalPadding,
            )
            .clip(
                shape = MaterialTheme.shapes.medium,
            )
            .shimmer(),
    )
}

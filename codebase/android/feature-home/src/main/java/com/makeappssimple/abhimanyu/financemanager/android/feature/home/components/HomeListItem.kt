package com.makeappssimple.abhimanyu.financemanager.android.feature.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.getReadableDateAndTimeString
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MyEmojiCircle
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.getAmountTextColor
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.R

data class HomeListItemViewData(
    val transaction: Transaction,
    val category: Category? = null,
    val sourceFrom: Source? = null,
    val sourceTo: Source? = null,
)

@Composable
internal fun HomeListItem(
    data: HomeListItemViewData,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                shape = MaterialTheme.shapes.large,
            )
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 8.dp,
                bottom = 8.dp,
            ),
    ) {
        MyEmojiCircle(
            backgroundColor = MaterialTheme.colorScheme.outline,
            emoji = data.category?.emoji,
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 8.dp,
                ),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                MyText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(
                            weight = 1F,
                        ),
                    text = data.transaction.title,
                    style = MaterialTheme.typography.headlineMedium
                        .copy(
                            color = MaterialTheme.colorScheme.onBackground,
                        ),
                )
                MyText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(
                            weight = 1F,
                        ),
                    text = if (data.transaction.transactionType == TransactionType.INCOME ||
                        (data.transaction.transactionType == TransactionType.ADJUSTMENT
                                && data.transaction.amount.value > 0)
                    ) {
                        data.transaction.amount.toSignedString()
                    } else {
                        data.transaction.amount.toString()
                    },
                    style = MaterialTheme.typography.headlineMedium
                        .copy(
                            color = data.transaction.getAmountTextColor(),
                            textAlign = TextAlign.End,
                        ),
                )
            }
            Spacer(
                modifier = Modifier
                    .height(
                        height = 4.dp,
                    ),
            )
            MyText(
                modifier = Modifier
                    .fillMaxWidth(),
                text = data.transaction.transactionFor.title,
                style = MaterialTheme.typography.bodySmall
                    .copy(
                        color = MaterialTheme.colorScheme.onBackground,
                    ),
            )
            Spacer(
                modifier = Modifier
                    .height(
                        height = 4.dp,
                    ),
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                MyText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(
                            weight = 1F,
                        ),
                    text = getReadableDateAndTimeString(
                        timestamp = data.transaction.transactionTimestamp,
                    ),
                    style = MaterialTheme.typography.bodySmall
                        .copy(
                            color = MaterialTheme.colorScheme.onBackground,
                        ),
                )
                MyText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(
                            weight = 1F,
                        ),
                    text = if (data.sourceFrom != null && data.sourceTo != null) {
                        stringResource(
                            id = R.string.list_item_home_source,
                            data.sourceFrom.name,
                            data.sourceTo.name,
                        )
                    } else {
                        data.sourceFrom?.name ?: data.sourceTo?.name.orEmpty()
                    },
                    style = MaterialTheme.typography.bodySmall
                        .copy(
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.End,
                        ),
                )
            }
        }
    }
}

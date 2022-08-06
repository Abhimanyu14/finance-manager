package com.makeappssimple.abhimanyu.financemanager.android.feature.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.database.utils.getReadableDateAndTimeString
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.EmojiCircle
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.ExpandableItemViewWrapper
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.utils.getAmountTextColor
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
    ExpandableItemViewWrapper(
        expanded = false,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    shape = RoundedCornerShape(
                        size = 24.dp,
                    ),
                )
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 8.dp,
                    bottom = 8.dp,
                ),
        ) {
            EmojiCircle(
                emoji = data.category?.emoji,
                backgroundColor = MaterialTheme.colorScheme.outline,
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
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
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
                        style = TextStyle(
                            color = data.transaction.getAmountTextColor(),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
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
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
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
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
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
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.End,
                        ),
                    )
                }
            }
        }
    }
}

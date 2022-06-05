package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.makeappssimple.abhimanyu.financemanager.android.R
import com.makeappssimple.abhimanyu.financemanager.android.entities.category.Category
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.Source
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.amountTextColor
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.EmojiCircle
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.ExpandableItemViewWrapper
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.MyText
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.DarkGray
import com.makeappssimple.abhimanyu.financemanager.android.utils.getReadableDateAndTimeString

data class HomeListItemViewData(
    val transaction: Transaction,
    val category: Category? = null,
    val sourceFrom: Source? = null,
    val sourceTo: Source? = null,
)

@Composable
fun HomeListItem(
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
                        text = data.transaction.title,
                        style = TextStyle(
                            color = DarkGray,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(
                                weight = 1F,
                            ),
                    )
                    MyText(
                        text = if (data.transaction.transactionType == TransactionType.INCOME ||
                            (data.transaction.transactionType == TransactionType.ADJUSTMENT
                                    && data.transaction.amount.value > 0)
                        ) {
                            data.transaction.amount.toSignedString()
                        } else {
                            data.transaction.amount.toString()
                        },
                        textAlign = TextAlign.End,
                        style = TextStyle(
                            color = data.transaction.amountTextColor,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(
                                weight = 1F,
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
                    text = data.transaction.transactionFor.title,
                    style = TextStyle(
                        color = DarkGray,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                    ),
                    modifier = Modifier
                        .fillMaxWidth(),
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
                        text = getReadableDateAndTimeString(
                            timestamp = data.transaction.transactionTimestamp,
                        ),
                        style = TextStyle(
                            color = DarkGray,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(
                                weight = 1F,
                            ),
                    )
                    MyText(
                        text = if (data.sourceFrom != null && data.sourceTo != null) {
                            stringResource(
                                id = R.string.list_item_home_source,
                                data.sourceFrom.name,
                                data.sourceTo.name,
                            )
                        } else {
                            data.sourceFrom?.name ?: data.sourceTo?.name.orEmpty()
                        },
                        textAlign = TextAlign.End,
                        style = TextStyle(
                            color = DarkGray,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(
                                weight = 1F,
                            ),
                    )
                }
            }
        }
    }
}

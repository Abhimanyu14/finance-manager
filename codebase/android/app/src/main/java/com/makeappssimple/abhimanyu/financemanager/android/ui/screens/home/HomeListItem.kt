package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DeleteForever
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
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
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.EmojiCircle
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.getDismissState
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Surface
import com.makeappssimple.abhimanyu.financemanager.android.utils.getDateAndTimeString

data class HomeListItemViewData(
    val transaction: Transaction,
    val category: Category? = null,
    val sourceFrom: Source? = null,
    val sourceTo: Source? = null,
)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeListItem(
    data: HomeListItemViewData,
    swipeToDelete: Boolean = true,
    deleteTransaction: () -> Unit,
) {
    if (swipeToDelete) {
        val dismissState = getDismissState(
            dismissedToEndAction = {
                deleteTransaction()
            },
        )

        SwipeToDismiss(
            state = dismissState,
            directions = mutableSetOf(
                DismissDirection.StartToEnd,
            ),
            background = {
                val color by animateColorAsState(
                    when (dismissState.targetValue) {
                        DismissValue.Default -> LightGray
                        DismissValue.DismissedToEnd -> Red
                        DismissValue.DismissedToStart -> White
                    }
                )
                val scale by animateFloatAsState(
                    if (dismissState.targetValue == DismissValue.Default) {
                        1f
                    } else {
                        1.25f
                    }
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = color,
                        ),
                ) {
                    Icon(
                        imageVector = Icons.Rounded.DeleteForever,
                        contentDescription = null,
                        tint = White,
                        modifier = Modifier
                            .weight(
                                weight = 1F,
                            )
                            .scale(
                                scale = scale,
                            )
                            .padding(
                                start = 16.dp,
                            ),
                    )
                }
            },
        ) {
            HomeListItemView(
                data = data,
            )
        }
    } else {
        HomeListItemView(
            data = data,
        )
    }
}

@Composable
private fun HomeListItemView(
    data: HomeListItemViewData,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Surface,
            )
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp,
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
                Text(
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
                Text(
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
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Text(
                    text = getDateAndTimeString(
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
                Text(
                    text = if (data.sourceFrom != null && data.sourceTo != null) {
                        stringResource(
                            id = R.string.list_item_sources,
                            data.sourceFrom.name,
                            data.sourceTo.name,
                        )
                    } else {
                        data.sourceFrom?.name ?: (data.sourceTo?.name ?: "")
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

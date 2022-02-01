package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.makeappssimple.abhimanyu.financemanager.android.models.Source
import com.makeappssimple.abhimanyu.financemanager.android.models.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.models.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.getDismissState
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.OnTertiaryContainer
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Surface
import com.makeappssimple.abhimanyu.financemanager.android.utils.getDateString

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeListItem(
    transaction: Transaction,
    source: Source?,
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
                        DismissValue.Default -> Color.LightGray
                        DismissValue.DismissedToEnd -> Red
                        DismissValue.DismissedToStart -> Color.White
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
                        tint = Color.White,
                        modifier = Modifier
                            .weight(
                                weight = 1f,
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
                transaction = transaction,
                source = source,
            )
        }
    } else {
        HomeListItemView(
            transaction = transaction,
            source = source,
        )
    }
}

@Composable
private fun HomeListItemView(
    transaction: Transaction,
    source: Source?,
) {
    Column(
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
        Row(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Text(
                text = transaction.title,
                style = TextStyle(
                    color = DarkGray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(
                        weight = 1F,
                    ),
            )
            Text(
                text = transaction.amount.toString(),
                textAlign = TextAlign.End,
                style = TextStyle(
                    color = when (transaction.transactionType) {
                        TransactionType.INCOME -> {
                            OnTertiaryContainer
                        }
                        TransactionType.EXPENSE -> {
                            Red
                        }
                        TransactionType.TRANSFER -> {
                            DarkGray
                        }
                    },
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(
                        weight = 1F,
                    ),
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Text(
                text = getDateString(
                    timestamp = transaction.transactionTimestamp,
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
                text = source?.name ?: "",
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

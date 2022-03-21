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
import com.makeappssimple.abhimanyu.financemanager.android.models.amountTextColor
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.getDismissState
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Surface
import com.makeappssimple.abhimanyu.financemanager.android.utils.getDateAndTimeString

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeListItem(
    transaction: Transaction,
    sourceFrom: Source? = null,
    sourceTo: Source? = null,
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
                sourceFrom = sourceFrom,
                sourceTo = sourceTo,
            )
        }
    } else {
        HomeListItemView(
            transaction = transaction,
            sourceFrom = sourceFrom,
            sourceTo = sourceTo,
        )
    }
}

@Composable
private fun HomeListItemView(
    transaction: Transaction,
    sourceFrom: Source?,
    sourceTo: Source?,
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
                    fontWeight = FontWeight.Bold,
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
                    color = transaction.transactionType.amountTextColor,
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
            modifier = Modifier.height(
                height = 4.dp,
            ),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Text(
                text = getDateAndTimeString(
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
                text = if (sourceFrom != null && sourceTo != null) {
                    "${sourceFrom.name} -> ${sourceTo.name}"
                } else {
                    sourceFrom?.name ?: (sourceTo?.name ?: "")
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

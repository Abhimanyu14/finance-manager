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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.makeappssimple.abhimanyu.financemanager.android.models.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.getDismissState
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Surface

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeListItem(
    transaction: Transaction,
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
                        DismissValue.DismissedToEnd -> Color.Red
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
            )
        }
    } else {
        HomeListItemView(
            transaction = transaction,
        )
    }
}

@Composable
private fun HomeListItemView(
    transaction: Transaction,
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
        Column {
            Text(
                text = transaction.amount.toString(),
                style = TextStyle(
                    color = DarkGray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                ),
            )
            Text(
                text = transaction.title,
                style = TextStyle(
                    color = DarkGray,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            )
        }
    }
}

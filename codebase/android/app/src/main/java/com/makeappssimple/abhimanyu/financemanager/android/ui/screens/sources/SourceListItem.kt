package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.sources

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.DeleteForever
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.Source
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.getDismissState
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Primary
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Surface
import com.makeappssimple.abhimanyu.financemanager.android.utils.getIcon

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SourceListItem(
    source: Source,
    expanded: Boolean,
    swipeToDeleteEnabled: Boolean,
    deleteSource: () -> Unit,
    onClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    if (swipeToDeleteEnabled) {
        val dismissState = getDismissState(
            dismissedToEndAction = {
                deleteSource()
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
            SourceListItemView(
                source = source,
                expanded = expanded,
                onClick = onClick,
                onEditClick = onEditClick,
                onDeleteClick = onDeleteClick,
            )
        }
    } else {
        SourceListItemView(
            source = source,
            expanded = expanded,
            onClick = onClick,
            onEditClick = onEditClick,
            onDeleteClick = onDeleteClick,
        )
    }
}

@Composable
fun SourceListItemView(
    source: Source,
    expanded: Boolean,
    onClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 8.dp,
                vertical = 0.dp,
            )
            .clip(
                shape = RoundedCornerShape(
                    size = 24.dp,
                ),
            )
            .background(
                color = if (expanded) {
                    White
                } else {
                    Surface
                },
            )
            .animateContentSize(),
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
                .clickable {
                    onClick()
                }
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = if (expanded) {
                        16.dp
                    } else {
                        8.dp
                    },
                    bottom = if (expanded) {
                        16.dp
                    } else {
                        8.dp
                    },
                ),
        ) {
            Icon(
                imageVector = getIcon(
                    name = source.type.title,
                ),
                contentDescription = null,
                tint = Primary,
                modifier = Modifier
                    .padding(
                        end = 8.dp,
                    ),
            )
            Text(
                text = source.name,
                style = TextStyle(
                    color = DarkGray,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                ),
                modifier = Modifier
                    .weight(
                        weight = 1F,
                    ),
            )
            Text(
                text = source.balanceAmount.toString(),
                style = TextStyle(
                    color = DarkGray,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                ),
            )
        }
        if (expanded) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 4.dp,
                        bottom = 16.dp,
                    ),
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(
                            weight = 1F,
                        )
                        .clip(
                            shape = CircleShape,
                        )
                        .clickable {
                            onEditClick()
                        },
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Edit,
                        contentDescription = null,
                    )
                    Text(
                        text = "Edit",
                        style = TextStyle(
                            color = DarkGray,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                        ),
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(
                            weight = 1F,
                        )
                        .clip(
                            shape = CircleShape,
                        )
                        .clickable {
                            onDeleteClick()
                        },
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Delete,
                        contentDescription = null,
                    )
                    Text(
                        text = "Delete",
                        style = TextStyle(
                            color = DarkGray,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                        ),
                    )
                }
            }
        }
    }
}

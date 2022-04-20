package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.sources

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.Source
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.conditionalClickable
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Blue50
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Primary
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Surface
import com.makeappssimple.abhimanyu.financemanager.android.utils.getIcon

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SourceListItem(
    source: Source,
    expanded: Boolean,
    onClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    ExpandableItemViewWrapper(
        expanded = expanded,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    shape = if (expanded) {
                        RoundedCornerShape(
                            topStart = 24.dp,
                            topEnd = 24.dp,
                        )
                    } else {
                        RoundedCornerShape(
                            size = 24.dp,
                        )
                    },
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
            Divider(
                color = LightGray,
                thickness = 0.5.dp,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 8.dp,
                        bottom = 8.dp,
                    ),
            ) {
                ExpandableItemIconButton(
                    iconImageVector = Icons.Rounded.Edit,
                    labelText = "Edit",
                    enabled = true,
                    onClick = onEditClick,
                    modifier = Modifier
                        .weight(
                            weight = 1F,
                        ),
                )
                ExpandableItemIconButton(
                    iconImageVector = Icons.Rounded.Delete,
                    labelText = "Delete",
                    enabled = !source.name.contains(
                        other = "Cash",
                        ignoreCase = false,
                    ),
                    onClick = onDeleteClick,
                    modifier = Modifier
                        .weight(
                            weight = 1F,
                        ),
                )
            }
        }
    }
}

@Composable
fun ExpandableItemIconButton(
    iconImageVector: ImageVector,
    labelText: String,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clip(
                shape = CircleShape,
            )
            .conditionalClickable(
                onClick = if (enabled) {
                    onClick
                } else {
                    null
                }
            ),
    ) {
        Icon(
            imageVector = iconImageVector,
            contentDescription = null,
            tint = if (enabled) {
                DarkGray
            } else {
                LightGray
            },
        )
        Text(
            text = labelText,
            style = TextStyle(
                color = if (enabled) {
                    DarkGray
                } else {
                    LightGray
                },
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
            ),
        )
    }
}

@Composable
fun ExpandableItemViewWrapper(
    expanded: Boolean,
    content: @Composable ColumnScope.() -> Unit,
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
                    Blue50
                } else {
                    Surface
                },
            )
            .animateContentSize(),
    ) {
        content()
    }
}

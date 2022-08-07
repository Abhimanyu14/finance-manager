package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.components

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.ExpandedListItemShape
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.DefaultTag
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.EmojiCircle
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.ExpandableItemIconButton
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.ExpandableItemViewWrapper
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.utils.isDefaultCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.utils.isSalaryCategory
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.R

@Composable
internal fun CategoriesListItem(
    category: Category,
    expanded: Boolean,
    deleteEnabled: Boolean,
    isDefault: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
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
                        ExpandedListItemShape
                    } else {
                        MaterialTheme.shapes.large
                    },
                )
                .combinedClickable(
                    onClick = {
                        onClick()
                    },
                    onLongClick = {
                        onLongClick()
                    },
                )
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
            EmojiCircle(
                emoji = category.emoji,
                backgroundColor = MaterialTheme.colorScheme.outline,
            )
            MyText(
                modifier = Modifier
                    .padding(
                        start = 8.dp,
                        end = 16.dp,
                    ),
                text = category.title,
                style = MaterialTheme.typography.headlineMedium
                    .copy(
                        color = MaterialTheme.colorScheme.onBackground,
                    ),
            )
            if (isDefault) {
                DefaultTag()
            }
        }
        if (expanded) {
            Divider(
                color = MaterialTheme.colorScheme.outline,
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
                    labelText = stringResource(
                        id = R.string.list_item_categories_edit,
                    ),
                    enabled = true,
                    onClick = onEditClick,
                    modifier = Modifier
                        .weight(
                            weight = 1F,
                        ),
                )
                ExpandableItemIconButton(
                    iconImageVector = Icons.Rounded.Delete,
                    labelText = stringResource(
                        id = R.string.list_item_categories_delete,
                    ),
                    enabled = !isDefaultCategory(
                        category = category.title,
                    ) && !isSalaryCategory(
                        category = category.title,
                    ) && deleteEnabled,
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

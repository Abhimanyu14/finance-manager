package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.select_list_item

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.extensions.conditionalClickable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyDefaultTag

@Immutable
internal data class SelectListItemBottomSheetItemData(
    val isDefault: Boolean = false,
    val isSelected: Boolean = false,
    val icon: ImageVector? = null,
    val primaryText: String,
    val secondaryText: String? = null,
    val onClick: () -> Unit,
)

@Composable
internal fun SelectListItemBottomSheetItem(
    data: SelectListItemBottomSheetItemData,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .conditionalClickable(
                onClickLabel = data.primaryText,
                role = Role.Button,
                onClick = data.onClick,
            )
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp,
            ),
    ) {
        data.icon?.let {
            Icon(
                imageVector = data.icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(
                        end = 8.dp,
                    ),
            )
        }
        MyText(
            modifier = Modifier,
            text = data.primaryText,
            style = MaterialTheme.typography.headlineMedium
                .copy(
                    color = if (data.isSelected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onBackground
                    },
                ),
        )
        if (data.isDefault) {
            MyDefaultTag()
        }
        Spacer(
            modifier = Modifier
                .weight(
                    weight = 1F,
                ),
        )
        data.secondaryText?.let {
            MyText(
                modifier = Modifier,
                text = data.secondaryText,
                style = MaterialTheme.typography.headlineMedium
                    .copy(
                        color = if (data.isSelected) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onBackground
                        },
                    ),
            )
        }
    }
}

package com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.bottom_sheet.select_source

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.DarkGray
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.Primary
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.R
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.bottom_sheet.BottomSheetTitle
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.utils.getIcon

data class SelectSourceBottomSheetItemData(
    val text: String,
    val iconKey: String,
    val isSelected: Boolean,
    val onClick: () -> Unit,
)

data class SelectSourceBottomSheetData(
    val items: List<SelectSourceBottomSheetItemData>,
)

@Composable
fun SelectSourceBottomSheet(
    data: SelectSourceBottomSheetData,
) {
    LazyColumn(
        modifier = Modifier
            .defaultMinSize(
                minHeight = 24.dp,
            ),
    ) {
        stickyHeader {
            BottomSheetTitle(
                textStringResourceId = R.string.bottom_sheet_select_source_title,
            )
        }
        items(
            items = data.items,
            key = { listItem ->
                listItem.hashCode()
            },
        ) { listItem ->
            SelectSourceBottomSheetItem(
                data = listItem,
            )
        }
    }
}

@Composable
private fun SelectSourceBottomSheetItem(
    data: SelectSourceBottomSheetItemData,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClickLabel = data.text,
                role = Role.Button,
                onClick = data.onClick,
            )
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp,
            ),
    ) {
        Icon(
            imageVector = getIcon(
                name = data.iconKey,
            ),
            contentDescription = null,
            tint = Primary,
            modifier = Modifier
                .padding(
                    end = 8.dp,
                ),
        )
        MyText(
            modifier = Modifier,
            text = data.text,
            style = TextStyle(
                color = DarkGray,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
            ),
        )
        Spacer(
            modifier = Modifier
                .weight(
                    weight = 1F,
                ),
        )
        if (data.isSelected) {
            Icon(
                imageVector = Icons.Rounded.Done,
                contentDescription = null,
            )
        }
    }
}

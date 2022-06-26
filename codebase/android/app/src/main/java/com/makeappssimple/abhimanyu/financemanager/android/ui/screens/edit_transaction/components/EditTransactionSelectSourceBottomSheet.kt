package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.edit_transaction.components

import androidx.compose.foundation.background
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
import com.makeappssimple.abhimanyu.financemanager.android.R
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.MyText
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.DarkGray
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Primary
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Surface
import com.makeappssimple.abhimanyu.financemanager.android.utils.getIcon

data class EditTransactionSelectSourceBottomSheetItemData(
    val text: String,
    val iconKey: String,
    val isSelected: Boolean,
    val onClick: () -> Unit,
)

data class EditTransactionSelectSourceBottomSheetData(
    val items: List<EditTransactionSelectSourceBottomSheetItemData>,
)

@Composable
fun EditTransactionSelectSourceBottomSheet(
    data: EditTransactionSelectSourceBottomSheetData,
) {
    LazyColumn(
        modifier = Modifier
            .defaultMinSize(
                minHeight = 24.dp,
            ),
    ) {
        stickyHeader {
            MyText(
                textStringResourceId = R.string.bottom_sheet_add_transaction_select_source_title,
                style = TextStyle(
                    color = DarkGray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Surface,
                    )
                    .padding(
                        all = 16.dp,
                    ),
            )
        }
        items(
            items = data.items,
        ) { editTransactionSelectSourceBottomSheetItemData ->
            EditTransactionSelectSourceBottomSheetItem(
                data = editTransactionSelectSourceBottomSheetItemData,
            )
        }
    }
}

@Composable
private fun EditTransactionSelectSourceBottomSheetItem(
    data: EditTransactionSelectSourceBottomSheetItemData,
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
                all = 16.dp,
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
            text = data.text,
            color = DarkGray,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier,
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

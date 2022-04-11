package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_transaction

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.makeappssimple.abhimanyu.financemanager.android.R
import com.makeappssimple.abhimanyu.financemanager.android.entities.category.Category
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.EmojiCircle

data class AddTransactionSelectCategoryBottomSheetItemData(
    val category: Category,
    val onClick: () -> Unit,
)

data class AddTransactionSelectCategoryBottomSheetData(
    val items: List<AddTransactionSelectCategoryBottomSheetItemData>,
)

@Composable
fun AddTransactionSelectCategoryBottomSheet(
    data: AddTransactionSelectCategoryBottomSheetData,
) {
    LazyColumn(
        modifier = Modifier
            .defaultMinSize(
                minHeight = 24.dp,
            ),
    ) {
        item {
            Text(
                text = stringResource(
                    id = R.string.bottom_sheet_add_transaction_select_category_title,
                ),
                style = TextStyle(
                    color = Color.DarkGray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        all = 16.dp,
                    ),
            )
        }
        items(
            items = data.items,
        ) { addTransactionSelectCategoryBottomSheetItemData ->
            AddTransactionSelectCategoryBottomSheetItem(
                data = addTransactionSelectCategoryBottomSheetItemData,
            )
        }
    }
}

@Composable
private fun AddTransactionSelectCategoryBottomSheetItem(
    data: AddTransactionSelectCategoryBottomSheetItemData,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClickLabel = data.category.title,
                role = Role.Button,
                onClick = data.onClick,
            )
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp,
            ),
    ) {
        EmojiCircle(
            emoji = data.category.emoji,
        )
        Text(
            text = data.category.title,
            color = Color.DarkGray,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(
                    start = 8.dp,
                ),
        )
    }
}

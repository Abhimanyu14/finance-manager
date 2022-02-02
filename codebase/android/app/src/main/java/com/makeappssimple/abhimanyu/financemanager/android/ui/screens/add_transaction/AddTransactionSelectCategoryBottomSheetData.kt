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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class AddTransactionSelectCategoryBottomSheetItemData(
    val text: String,
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
        Text(
            text = data.text,
            color = Color.DarkGray,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier,
        )
    }
}

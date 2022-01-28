package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_transaction

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class AddTransactionSelectSourceBottomSheetItemData(
    val text: String,
    val onClick: () -> Unit,
)

data class AddTransactionSelectSourceBottomSheetData(
    val list: List<AddTransactionSelectSourceBottomSheetItemData>,
)

@Composable
fun AddTransactionSelectSourceBottomSheet(
    data: AddTransactionSelectSourceBottomSheetData,
) {
    Column(
        modifier = Modifier
            .defaultMinSize(
                minHeight = 24.dp,
            ),
    ) {
        data.list.forEach { addTransactionSelectSourceBottomSheetItemData ->
            AddTransactionSelectSourceBottomSheetItem(
                data = addTransactionSelectSourceBottomSheetItemData,
            )
        }
    }
}

@Composable
private fun AddTransactionSelectSourceBottomSheetItem(
    data: AddTransactionSelectSourceBottomSheetItemData,
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
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier,
        )
    }
}

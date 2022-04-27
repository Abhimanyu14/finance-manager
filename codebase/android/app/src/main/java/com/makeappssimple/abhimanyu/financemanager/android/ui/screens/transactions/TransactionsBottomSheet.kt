package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.transactions

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

data class TransactionsBottomSheetItemData(
    val text: String,
    val onClick: () -> Unit,
)

data class TransactionsBottomSheetData(
    val items: List<TransactionsBottomSheetItemData>,
)

@Composable
fun TransactionsBottomSheet(
    data: TransactionsBottomSheetData,
) {
    LazyColumn(
        modifier = Modifier
            .padding(
                top = 16.dp,
            )
            .defaultMinSize(
                minHeight = 24.dp,
            ),
    ) {
        items(
            items = data.items,
        ) { transactionsBottomSheetItemData ->
            TransactionsBottomSheetItem(
                data = transactionsBottomSheetItemData,
            )
        }
    }
}

@Composable
private fun TransactionsBottomSheetItem(
    data: TransactionsBottomSheetItemData,
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

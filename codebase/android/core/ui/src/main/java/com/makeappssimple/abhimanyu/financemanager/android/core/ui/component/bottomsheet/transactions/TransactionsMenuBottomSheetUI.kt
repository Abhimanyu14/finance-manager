package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.transactions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.common.MyBottomSheetList
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.common.MyBottomSheetListData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.common.MyBottomSheetListItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.common.MyBottomSheetListItemDataAndEventHandler
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.common.MyBottomSheetListItemEvent

@Immutable
public data class TransactionsMenuBottomSheetItemData(
    val imageVector: ImageVector? = null,
    val text: String,
    val onClick: () -> Unit,
)

@Composable
public fun TransactionsMenuBottomSheetUI(
    modifier: Modifier = Modifier,
    items: List<TransactionsMenuBottomSheetItemData>,
) {
    MyBottomSheetList(
        modifier = modifier,
        data = MyBottomSheetListData(
            items = items.map { itemData ->
                MyBottomSheetListItemDataAndEventHandler(
                    data = MyBottomSheetListItemData(
                        imageVector = itemData.imageVector,
                        text = itemData.text,
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is MyBottomSheetListItemEvent.OnClick -> {
                                itemData.onClick()
                            }
                        }
                    },
                )
            },
        ),
    )
}

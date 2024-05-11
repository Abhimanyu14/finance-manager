package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.transactionfor

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
public data class TransactionForValuesMenuBottomSheetItemData(
    val imageVector: ImageVector? = null,
    val text: String,
    val onClick: () -> Unit,
)

@Composable
public fun TransactionForValuesMenuBottomSheetUI(
    modifier: Modifier = Modifier,
    items: List<TransactionForValuesMenuBottomSheetItemData>,
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

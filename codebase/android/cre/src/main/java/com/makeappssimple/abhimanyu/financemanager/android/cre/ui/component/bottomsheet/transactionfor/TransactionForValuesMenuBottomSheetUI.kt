package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.bottomsheet.transactionfor

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.bottomsheet.common.MyBottomSheetList
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.bottomsheet.common.MyBottomSheetListData
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.bottomsheet.common.MyBottomSheetListItemData
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.bottomsheet.common.MyBottomSheetListItemDataAndEventHandler
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.bottomsheet.common.MyBottomSheetListItemEvent
import kotlinx.collections.immutable.ImmutableList

@Composable
public fun TransactionForValuesMenuBottomSheetUI(
    modifier: Modifier = Modifier,
    items: ImmutableList<TransactionForValuesMenuBottomSheetItemData>,
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

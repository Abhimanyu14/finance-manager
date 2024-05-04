package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.account

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.common.MyBottomSheetListData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.common.MyBottomSheetListItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.common.MyBottomSheetListItemDataAndEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.common.MyBottomSheetListItemEvents

@Immutable
public data class AccountsMenuBottomSheetItemData(
    val imageVector: ImageVector? = null,
    val text: String,
    val onClick: () -> Unit,
)

@Composable
public fun AccountsMenuBottomSheetUI(
    modifier: Modifier = Modifier,
    items: List<AccountsMenuBottomSheetItemData>,
) {
    MyBottomSheetListData(
        modifier = modifier,
        data = MyBottomSheetListData(
            items = items.map {
                MyBottomSheetListItemDataAndEvents(
                    data = MyBottomSheetListItemData(
                        imageVector = it.imageVector,
                        text = it.text,
                    ),
                    events = MyBottomSheetListItemEvents(
                        onClick = it.onClick,
                    ),
                )
            },
        ),
    )
}
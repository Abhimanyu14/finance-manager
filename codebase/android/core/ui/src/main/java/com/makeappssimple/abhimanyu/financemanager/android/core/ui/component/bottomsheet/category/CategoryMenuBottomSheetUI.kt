package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.category

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.common.MyBottomSheetListData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.common.MyBottomSheetListItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.common.MyBottomSheetListItemDataAndEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.common.MyBottomSheetListItemEvent

@Immutable
public data class CategoryMenuBottomSheetItemData(
    val imageVector: ImageVector? = null,
    val text: String,
    val onClick: () -> Unit,
)

@Composable
public fun CategoryMenuBottomSheetUI(
    modifier: Modifier = Modifier,
    items: List<CategoryMenuBottomSheetItemData>,
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
                    handleEvent = { event ->
                        when (event) {
                            is MyBottomSheetListItemEvent.OnClick -> {
                                it.onClick()
                            }
                        }
                    },
                )
            },
        ),
    )
}

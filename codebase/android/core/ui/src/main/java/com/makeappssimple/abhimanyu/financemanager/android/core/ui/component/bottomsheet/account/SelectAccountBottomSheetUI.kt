package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.account

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.NavigationBarsAndImeSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.common.MyBottomSheetTitle
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.AccountsListItem
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.AccountsListItemDataAndEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.minimumBottomSheetHeight

@Immutable
internal data class SelectListItemBottomSheetUIData(
    val titleTextStringResourceId: Int = 0,
    val data: List<AccountsListItemDataAndEvents> = emptyList(),
)

@Composable
internal fun SelectAccountBottomSheetUI(
    modifier: Modifier = Modifier,
    data: SelectListItemBottomSheetUIData,
) {
    LazyColumn(
        modifier = modifier
            .defaultMinSize(
                minHeight = minimumBottomSheetHeight,
            ),
    ) {
        stickyHeader {
            MyBottomSheetTitle(
                textStringResourceId = data.titleTextStringResourceId,
            )
        }
        items(
            items = data.data,
            key = { listItem ->
                listItem.hashCode()
            },
        ) { listItem ->
            AccountsListItem(
                data = listItem.data,
                events = listItem.events,
            )
        }
        item {
            NavigationBarsAndImeSpacer()
        }
    }
}
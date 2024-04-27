package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.account

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.NavigationBarsAndImeSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.common.MyBottomSheetTitle
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.accounts.AccountsListItemContent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.accounts.AccountsListItemContentDataAndEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.minimumBottomSheetHeight

@Immutable
internal data class SelectAccountListItemBottomSheetUIData(
    @StringRes val titleTextStringResourceId: Int = 0,
    val data: List<AccountsListItemContentDataAndEvents> = emptyList(),
)

@Composable
internal fun SelectAccountBottomSheetUI(
    modifier: Modifier = Modifier,
    data: SelectAccountListItemBottomSheetUIData,
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
            AccountsListItemContent(
                data = listItem.data,
                events = listItem.events,
            )
        }
        item {
            NavigationBarsAndImeSpacer()
        }
        item {
            VerticalSpacer(
                height = 16.dp,
            )
        }
    }
}

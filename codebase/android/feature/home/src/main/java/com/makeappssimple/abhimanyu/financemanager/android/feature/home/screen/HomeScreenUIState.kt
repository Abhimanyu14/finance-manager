package com.makeappssimple.abhimanyu.financemanager.android.feature.home.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.transaction_list_item.TransactionListItemData

@Stable
class HomeScreenUIState(
    data: HomeScreenUIData,
    val homeBottomSheetType: HomeBottomSheetType,
    val setHomeBottomSheetType: (HomeBottomSheetType) -> Unit,
) {
    val isBackupCardVisible: Boolean = data.isBackupCardVisible
    val transactionListItemDataList: List<TransactionListItemData> =
        data.transactionListItemDataList
    val resetBottomSheetType: () -> Unit = {
        setHomeBottomSheetType(HomeBottomSheetType.NONE)
    }
}

@Composable
fun rememberHomeScreenUIState(
    data: HomeScreenUIData,
): HomeScreenUIState {
    val (homeBottomSheetType: HomeBottomSheetType, setHomeBottomSheetType: (HomeBottomSheetType) -> Unit) = remember {
        mutableStateOf(
            value = HomeBottomSheetType.NONE,
        )
    }

    return remember(
        data,
        homeBottomSheetType,
        setHomeBottomSheetType,
    ) {
        HomeScreenUIState(
            data = data,
            homeBottomSheetType = homeBottomSheetType,
            setHomeBottomSheetType = setHomeBottomSheetType,
        )
    }
}

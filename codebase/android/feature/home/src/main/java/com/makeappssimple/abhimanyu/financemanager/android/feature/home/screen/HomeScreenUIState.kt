package com.makeappssimple.abhimanyu.financemanager.android.feature.home.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orFalse
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.transaction_list_item.TransactionListItemData

@Stable
class HomeScreenUIState(
    data: MyResult<HomeScreenUIData>?,
    val homeBottomSheetType: HomeBottomSheetType,
    val setHomeBottomSheetType: (HomeBottomSheetType) -> Unit,
) {
    private val unwrappedData = when (data) {
        is MyResult.Success -> {
            data.data
        }

        else -> {
            null
        }
    }

    val isLoading: Boolean = unwrappedData.isNull()
    val isBackupCardVisible: Boolean = unwrappedData?.isBackupCardVisible.orFalse()
    val transactionListItemDataList: List<TransactionListItemData> =
        unwrappedData?.transactionListItemDataList.orEmpty()
    val resetBottomSheetType: () -> Unit = {
        setHomeBottomSheetType(HomeBottomSheetType.NONE)
    }
}

@Composable
fun rememberHomeScreenUIState(
    data: MyResult<HomeScreenUIData>?,
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

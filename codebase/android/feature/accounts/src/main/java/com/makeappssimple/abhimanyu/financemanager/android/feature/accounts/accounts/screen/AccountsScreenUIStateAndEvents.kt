package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateAndEvents

@Stable
internal class AccountsScreenUIStateAndEvents(
    val state: AccountsScreenUIState,
    val events: AccountsScreenUIStateEvents,
) : ScreenUIStateAndEvents

@Immutable
internal data class AccountsScreenUIStateEvents(
    val resetScreenBottomSheetType: () -> Unit,
    val setAccountIdToDelete: (Int?) -> Unit,
    val setClickedItemId: (Int?) -> Unit,
    val setScreenBottomSheetType: (AccountsScreenBottomSheetType) -> Unit,
)

@Composable
internal fun rememberAccountsScreenUIStateAndEvents(
    data: MyResult<AccountsScreenUIData>?,
): AccountsScreenUIStateAndEvents {
    var clickedItemId: Int? by remember {
        mutableStateOf(
            value = null,
        )
    }
    var accountIdToDelete: Int? by remember {
        mutableStateOf(
            value = null,
        )
    }
    var screenBottomSheetType: AccountsScreenBottomSheetType by remember {
        mutableStateOf(
            value = AccountsScreenBottomSheetType.None,
        )
    }
    val setClickedItemId = { updatedClickedItemId: Int? ->
        clickedItemId = updatedClickedItemId
    }
    val setAccountIdToDelete = { updatedAccountIdToDelete: Int? ->
        accountIdToDelete = updatedAccountIdToDelete
    }
    val setScreenBottomSheetType =
        { updatedAccountsBottomSheetType: AccountsScreenBottomSheetType ->
            screenBottomSheetType = updatedAccountsBottomSheetType
        }

    return remember(
        data,
        clickedItemId,
        accountIdToDelete,
        screenBottomSheetType,
        setClickedItemId,
        setAccountIdToDelete,
        setScreenBottomSheetType,
    ) {
        val unwrappedData: AccountsScreenUIData? = when (data) {
            is MyResult.Success -> {
                data.data
            }

            else -> {
                null
            }
        }

        // TODO(Abhi): Can be reordered to match the class ordering
        AccountsScreenUIStateAndEvents(
            state = AccountsScreenUIState(
                screenBottomSheetType = screenBottomSheetType,
                isBottomSheetVisible = screenBottomSheetType != AccountsScreenBottomSheetType.None,
                clickedItemId = clickedItemId,
                accountIdToDelete = accountIdToDelete,
                isLoading = unwrappedData.isNull(),
                accountsListItemDataList = unwrappedData?.accountsListItemDataList.orEmpty(),
                accountsTotalBalanceAmountValue = unwrappedData?.accountsTotalBalanceAmountValue.orZero(),
                accountsTotalMinimumBalanceAmountValue = unwrappedData?.accountsTotalMinimumBalanceAmountValue.orZero(),
            ),
            events = AccountsScreenUIStateEvents(
                setScreenBottomSheetType = setScreenBottomSheetType,
                setClickedItemId = setClickedItemId,
                setAccountIdToDelete = setAccountIdToDelete,
                resetScreenBottomSheetType = {
                    setScreenBottomSheetType(AccountsScreenBottomSheetType.None)
                },
            ),
        )
    }
}

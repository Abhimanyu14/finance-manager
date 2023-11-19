package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.component.listitem.AccountsListItemData

@Stable
class AccountsScreenUIState(
    data: MyResult<AccountsScreenUIData>?,
    private val unwrappedData: AccountsScreenUIData? = when (data) {
        is MyResult.Success -> {
            data.data
        }

        else -> {
            null
        }
    },
    val clickedItemId: Int?,
    val expandedItemIndex: Int?,
    val accountIdToDelete: Int?,
    val accountsBottomSheetType: AccountsScreenBottomSheetType,
    val setClickedItemId: (Int?) -> Unit,
    val setExpandedItemIndex: (Int?) -> Unit,
    val setAccountIdToDelete: (Int?) -> Unit,
    val setAccountsBottomSheetType: (AccountsScreenBottomSheetType) -> Unit,
    val isLoading: Boolean = unwrappedData.isNull(),
    val accountsListItemDataList: List<AccountsListItemData> =
        unwrappedData?.accountsListItemDataList.orEmpty(),
    val accountsTotalBalanceAmountValue: Long =
        unwrappedData?.accountsTotalBalanceAmountValue.orZero(),
    val accountsTotalMinimumBalanceAmountValue: Long =
        unwrappedData?.accountsTotalMinimumBalanceAmountValue.orZero(),
    val resetBottomSheetType: () -> Unit = {
        setAccountsBottomSheetType(AccountsScreenBottomSheetType.NONE)
    },
) : ScreenUIState

@Composable
fun rememberAccountsScreenUIState(
    data: MyResult<AccountsScreenUIData>?,
): AccountsScreenUIState {
    var clickedItemId: Int? by remember {
        mutableStateOf(
            value = null,
        )
    }
    var expandedItemIndex: Int? by remember {
        mutableStateOf(
            value = null,
        )
    }
    var accountIdToDelete: Int? by remember {
        mutableStateOf(
            value = null,
        )
    }
    val (accountsBottomSheetType: AccountsScreenBottomSheetType, setAccountsBottomSheetType: (AccountsScreenBottomSheetType) -> Unit) = remember {
        mutableStateOf(
            value = AccountsScreenBottomSheetType.NONE,
        )
    }
    val setClickedItemId = { updatedClickedItemId: Int? ->
        clickedItemId = updatedClickedItemId
    }
    val setExpandedItemIndex = { updatedExpandedItemIndex: Int? ->
        expandedItemIndex = updatedExpandedItemIndex
    }
    val setAccountIdToDelete = { updatedAccountIdToDelete: Int? ->
        accountIdToDelete = updatedAccountIdToDelete
    }

    return remember(
        data,
        clickedItemId,
        expandedItemIndex,
        accountIdToDelete,
        accountsBottomSheetType,
        setClickedItemId,
        setExpandedItemIndex,
        setAccountIdToDelete,
        setAccountsBottomSheetType,
    ) {
        AccountsScreenUIState(
            data = data,
            accountsBottomSheetType = accountsBottomSheetType,
            expandedItemIndex = expandedItemIndex,
            clickedItemId = clickedItemId,
            accountIdToDelete = accountIdToDelete,
            setAccountsBottomSheetType = setAccountsBottomSheetType,
            setExpandedItemIndex = setExpandedItemIndex,
            setClickedItemId = setClickedItemId,
            setAccountIdToDelete = setAccountIdToDelete,
        )
    }
}

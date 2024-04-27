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
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.accounts.AccountsListItemData

@Stable
public class AccountsScreenUIState(
    data: MyResult<AccountsScreenUIData>?,
    private val unwrappedData: AccountsScreenUIData? = when (data) {
        is MyResult.Success -> {
            data.data
        }

        else -> {
            null
        }
    },
    public val clickedItemId: Int?,
    public val accountIdToDelete: Int?,
    public val screenBottomSheetType: AccountsScreenBottomSheetType,
    public val setClickedItemId: (Int?) -> Unit,
    public val setAccountIdToDelete: (Int?) -> Unit,
    public val setScreenBottomSheetType: (AccountsScreenBottomSheetType) -> Unit,
    public val isLoading: Boolean = unwrappedData.isNull(),
    public val accountsListItemDataList: List<AccountsListItemData> =
        unwrappedData?.accountsListItemDataList.orEmpty(),
    public val accountsTotalBalanceAmountValue: Long =
        unwrappedData?.accountsTotalBalanceAmountValue.orZero(),
    public val accountsTotalMinimumBalanceAmountValue: Long =
        unwrappedData?.accountsTotalMinimumBalanceAmountValue.orZero(),
    public val resetScreenBottomSheetType: () -> Unit = {
        setScreenBottomSheetType(AccountsScreenBottomSheetType.None)
    },
) : ScreenUIState

@Composable
public fun rememberAccountsScreenUIState(
    data: MyResult<AccountsScreenUIData>?,
): AccountsScreenUIState {
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
        AccountsScreenUIState(
            data = data,
            screenBottomSheetType = screenBottomSheetType,
            clickedItemId = clickedItemId,
            accountIdToDelete = accountIdToDelete,
            setScreenBottomSheetType = setScreenBottomSheetType,
            setClickedItemId = setClickedItemId,
            setAccountIdToDelete = setAccountIdToDelete,
        )
    }
}

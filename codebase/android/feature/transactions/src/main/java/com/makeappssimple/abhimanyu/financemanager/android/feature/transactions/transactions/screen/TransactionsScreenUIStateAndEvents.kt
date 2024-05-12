package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orMin
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.areFiltersSelected
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.orDefault
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.orEmpty
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateAndEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateEvents

@Stable
internal class TransactionsScreenUIStateAndEvents(
    val state: TransactionsScreenUIState,
    val events: TransactionsScreenUIStateEvents,
) : ScreenUIStateAndEvents

@Stable
internal class TransactionsScreenUIStateEvents(
    val resetScreenBottomSheetType: () -> Unit,
    val setIsInSelectionMode: (Boolean) -> Unit,
    val setScreenBottomSheetType: (TransactionsScreenBottomSheetType) -> Unit,
) : ScreenUIStateEvents

@Composable
internal fun rememberTransactionsScreenUIStateAndEvents(
    data: MyResult<TransactionsScreenUIData>?,
): TransactionsScreenUIStateAndEvents {
    val (isInSelectionMode: Boolean, setIsInSelectionMode: (Boolean) -> Unit) = remember {
        mutableStateOf(false)
    }
    var screenBottomSheetType: TransactionsScreenBottomSheetType by remember {
        mutableStateOf(
            value = TransactionsScreenBottomSheetType.None,
        )
    }
    val setScreenBottomSheetType =
        { updatedTransactionsScreenBottomSheetType: TransactionsScreenBottomSheetType ->
            screenBottomSheetType = updatedTransactionsScreenBottomSheetType
        }

    return remember(
        data,
        isInSelectionMode,
        screenBottomSheetType,
        setIsInSelectionMode,
        setScreenBottomSheetType,
    ) {
        val unwrappedData: TransactionsScreenUIData? = when (data) {
            is MyResult.Success -> {
                data.data
            }

            else -> {
                null
            }
        }

        // TODO(Abhi): Can be reordered to match the class ordering
        TransactionsScreenUIStateAndEvents(
            state = TransactionsScreenUIState(
                isBottomSheetVisible = screenBottomSheetType != TransactionsScreenBottomSheetType.None,
                isInSelectionMode = isInSelectionMode,
                isLoading = unwrappedData.isNull() || unwrappedData.isLoading,
                isSearchSortAndFilterVisible = isInSelectionMode.not() && (
                        unwrappedData?.transactionDetailsListItemViewData.orEmpty().isNotEmpty() ||
                                unwrappedData?.searchText.orEmpty().isNotEmpty() ||
                                unwrappedData?.selectedFilter.orEmpty().areFiltersSelected()
                        ),
                selectedFilter = unwrappedData?.selectedFilter.orEmpty(),
                selectedTransactions = unwrappedData?.selectedTransactions.orEmpty(),
                sortOptions = unwrappedData?.sortOptions.orEmpty(),
                transactionForValues = unwrappedData?.transactionForValues.orEmpty(),
                accounts = unwrappedData?.accounts.orEmpty(),
                expenseCategories = unwrappedData?.expenseCategories.orEmpty(),
                incomeCategories = unwrappedData?.incomeCategories.orEmpty(),
                investmentCategories = unwrappedData?.investmentCategories.orEmpty(),
                transactionTypes = unwrappedData?.transactionTypes.orEmpty(),
                currentLocalDate = unwrappedData?.currentLocalDate.orMin(),
                oldestTransactionLocalDate = unwrappedData?.oldestTransactionLocalDate.orMin(),
                transactionDetailsListItemViewData = unwrappedData?.transactionDetailsListItemViewData.orEmpty(),
                selectedSortOption = unwrappedData?.selectedSortOption.orDefault(),
                searchText = unwrappedData?.searchText.orEmpty(),
                screenBottomSheetType = screenBottomSheetType,
            ),
            events = TransactionsScreenUIStateEvents(
                resetScreenBottomSheetType = {
                    setScreenBottomSheetType(TransactionsScreenBottomSheetType.None)
                },
                setIsInSelectionMode = setIsInSelectionMode,
                setScreenBottomSheetType = setScreenBottomSheetType,
            ),
        )
    }
}

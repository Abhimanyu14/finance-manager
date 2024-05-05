package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.screen

import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyTabData
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.griditem.CategoriesGridItemData

@Stable
internal class CategoriesScreenUIState(
    val screenBottomSheetType: CategoriesScreenBottomSheetType,
    var categoryIdToDelete: Int?,
    var clickedItemId: Int?,
    val selectedTabIndex: Int,
    val tabData: List<MyTabData>,
    val validTransactionTypes: List<TransactionType>,
    val categoriesGridItemDataMap: Map<TransactionType, List<CategoriesGridItemData>>,
    val pagerState: PagerState,
    val resetScreenBottomSheetType: () -> Unit,
    val setCategoryIdToDelete: (Int?) -> Unit,
    val setClickedItemId: (Int?) -> Unit,
    val setScreenBottomSheetType: (CategoriesScreenBottomSheetType) -> Unit,
) : ScreenUIState

@Composable
internal fun rememberCategoriesScreenUIState(
    data: MyResult<CategoriesScreenUIData>?,
): CategoriesScreenUIState {
    var screenBottomSheetType: CategoriesScreenBottomSheetType by remember {
        mutableStateOf(
            value = CategoriesScreenBottomSheetType.None,
        )
    }
    var categoryIdToDelete: Int? by remember {
        mutableStateOf(
            value = null,
        )
    }
    var clickedItemId: Int? by remember {
        mutableStateOf(
            value = null,
        )
    }
    val pagerState: PagerState = rememberPagerState(
        pageCount = { 3 },
    )
    val setScreenBottomSheetType =
        { updatedCategoriesBottomSheetType: CategoriesScreenBottomSheetType ->
            screenBottomSheetType = updatedCategoriesBottomSheetType
        }
    val setCategoryIdToDelete = { updatedCategoryIdToDelete: Int? ->
        categoryIdToDelete = updatedCategoryIdToDelete
    }
    val setClickedItemId = { updatedClickedItemId: Int? ->
        clickedItemId = updatedClickedItemId
    }

    return remember(
        data,
        screenBottomSheetType,
        categoryIdToDelete,
        clickedItemId,
        pagerState,
        setScreenBottomSheetType,
        setCategoryIdToDelete,
        setClickedItemId,
    ) {
        val unwrappedData: CategoriesScreenUIData? = when (data) {
            is MyResult.Success -> {
                data.data
            }

            else -> {
                null
            }
        }
        val validTransactionTypes = listOf(
            TransactionType.EXPENSE,
            TransactionType.INCOME,
            TransactionType.INVESTMENT,
        )

        // TODO(Abhi): Can be reordered to match the class ordering
        CategoriesScreenUIState(
            screenBottomSheetType = screenBottomSheetType,
            categoryIdToDelete = categoryIdToDelete,
            clickedItemId = clickedItemId,
            selectedTabIndex = unwrappedData?.selectedTabIndex.orZero(),
            tabData = validTransactionTypes.map {
                MyTabData(
                    title = it.title,
                )
            },
            validTransactionTypes = listOf(
                TransactionType.EXPENSE,
                TransactionType.INCOME,
                TransactionType.INVESTMENT,
            ),
            categoriesGridItemDataMap =
            unwrappedData?.categoriesGridItemDataMap.orEmpty(),
            pagerState = pagerState,
            resetScreenBottomSheetType = {
                setScreenBottomSheetType(CategoriesScreenBottomSheetType.None)
            },
            setCategoryIdToDelete = setCategoryIdToDelete,
            setClickedItemId = setClickedItemId,
            setScreenBottomSheetType = setScreenBottomSheetType,
        )
    }
}

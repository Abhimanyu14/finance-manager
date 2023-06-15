package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyTabData
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.grid_item.CategoriesGridItemData

@Stable
class CategoriesScreenUIState(
    data: CategoriesScreenUIData,
    val categoriesBottomSheetType: CategoriesBottomSheetType,
    var categoryIdToDelete: Int?,
    var clickedItemId: Int?,
    val tabData: List<MyTabData>,
    val transactionTypes: List<TransactionType>,
    val pagerState: PagerState,
    val setCategoriesBottomSheetType: (CategoriesBottomSheetType) -> Unit,
    val setCategoryIdToDelete: (Int?) -> Unit,
    val setClickedItemId: (Int?) -> Unit,
) {
    val selectedTabIndex: Int = data.selectedTabIndex
    val categoriesGridItemDataMap: Map<TransactionType, List<CategoriesGridItemData>> =
        data.categoriesGridItemDataMap
    val resetBottomSheetType: () -> Unit = {
        setCategoriesBottomSheetType(CategoriesBottomSheetType.None)
    }
}

@Composable
fun rememberCategoriesScreenUIState(
    data: CategoriesScreenUIData,
): CategoriesScreenUIState {
    var categoriesBottomSheetType: CategoriesBottomSheetType by remember {
        mutableStateOf(
            value = CategoriesBottomSheetType.None,
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
    val transactionTypes: List<TransactionType> = listOf(
        TransactionType.EXPENSE,
        TransactionType.INCOME,
        TransactionType.INVESTMENT,
    )
    val tabData: List<MyTabData> = remember {
        transactionTypes
            .map {
                MyTabData(
                    title = it.title,
                )
            }
    }
    val pagerState: PagerState = rememberPagerState(
        initialPage = 0,
    )
    val setCategoriesBottomSheetType =
        { updatedCategoriesBottomSheetType: CategoriesBottomSheetType ->
            categoriesBottomSheetType = updatedCategoriesBottomSheetType
        }
    val setCategoryIdToDelete = { updatedCategoryIdToDelete: Int? ->
        categoryIdToDelete = updatedCategoryIdToDelete
    }
    val setClickedItemId = { updatedClickedItemId: Int? ->
        clickedItemId = updatedClickedItemId
    }

    return remember(
        data,
        categoriesBottomSheetType,
        categoryIdToDelete,
        clickedItemId,
        pagerState,
        setCategoriesBottomSheetType,
        setCategoryIdToDelete,
        setClickedItemId,
    ) {
        CategoriesScreenUIState(
            data = data,
            categoriesBottomSheetType = categoriesBottomSheetType,
            categoryIdToDelete = categoryIdToDelete,
            clickedItemId = clickedItemId,
            tabData = tabData,
            transactionTypes = transactionTypes,
            pagerState = pagerState,
            setCategoriesBottomSheetType = setCategoriesBottomSheetType,
            setCategoryIdToDelete = setCategoryIdToDelete,
            setClickedItemId = setClickedItemId,
        )
    }
}

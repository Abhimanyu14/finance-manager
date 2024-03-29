package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.screen

import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyTabData
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.griditem.CategoriesGridItemData

@Stable
class CategoriesScreenUIState(
    data: MyResult<CategoriesScreenUIData>?,
    private val unwrappedData: CategoriesScreenUIData? = when (data) {
        is MyResult.Success -> {
            data.data
        }

        else -> {
            null
        }
    },
    val screenBottomSheetType: CategoriesScreenBottomSheetType,
    var categoryIdToDelete: Int?,
    var clickedItemId: Int?,
    val pagerState: PagerState,
    val setScreenBottomSheetType: (CategoriesScreenBottomSheetType) -> Unit,
    val setCategoryIdToDelete: (Int?) -> Unit,
    val setClickedItemId: (Int?) -> Unit,
    val isLoading: Boolean = unwrappedData.isNull(),
    val selectedTabIndex: Int = unwrappedData?.selectedTabIndex.orZero(),
    val validTransactionTypes: List<TransactionType> = listOf(
        TransactionType.EXPENSE,
        TransactionType.INCOME,
        TransactionType.INVESTMENT,
    ),
    val tabData: List<MyTabData> = validTransactionTypes.map {
        MyTabData(
            title = it.title,
        )
    },
    val categoriesGridItemDataMap: Map<TransactionType, List<CategoriesGridItemData>> =
        unwrappedData?.categoriesGridItemDataMap.orEmpty(),
    val resetScreenBottomSheetType: () -> Unit = {
        setScreenBottomSheetType(CategoriesScreenBottomSheetType.None)
    },
) : ScreenUIState

@Composable
fun rememberCategoriesScreenUIState(
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
        CategoriesScreenUIState(
            data = data,
            screenBottomSheetType = screenBottomSheetType,
            categoryIdToDelete = categoryIdToDelete,
            clickedItemId = clickedItemId,
            pagerState = pagerState,
            setScreenBottomSheetType = setScreenBottomSheetType,
            setCategoryIdToDelete = setCategoryIdToDelete,
            setClickedItemId = setClickedItemId,
        )
    }
}

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
public class CategoriesScreenUIState(
    data: MyResult<CategoriesScreenUIData>?,
    private val unwrappedData: CategoriesScreenUIData? = when (data) {
        is MyResult.Success -> {
            data.data
        }

        else -> {
            null
        }
    },
    public val screenBottomSheetType: CategoriesScreenBottomSheetType,
    public var categoryIdToDelete: Int?,
    public var clickedItemId: Int?,
    public val pagerState: PagerState,
    public val setScreenBottomSheetType: (CategoriesScreenBottomSheetType) -> Unit,
    public val setCategoryIdToDelete: (Int?) -> Unit,
    public val setClickedItemId: (Int?) -> Unit,
    public val isLoading: Boolean = unwrappedData.isNull(),
    public val selectedTabIndex: Int = unwrappedData?.selectedTabIndex.orZero(),
    public val validTransactionTypes: List<TransactionType> = listOf(
        TransactionType.EXPENSE,
        TransactionType.INCOME,
        TransactionType.INVESTMENT,
    ),
    public val tabData: List<MyTabData> = validTransactionTypes.map {
        MyTabData(
            title = it.title,
        )
    },
    public val categoriesGridItemDataMap: Map<TransactionType, List<CategoriesGridItemData>> =
        unwrappedData?.categoriesGridItemDataMap.orEmpty(),
    public val resetScreenBottomSheetType: () -> Unit = {
        setScreenBottomSheetType(CategoriesScreenBottomSheetType.None)
    },
) : ScreenUIState

@Composable
public fun rememberCategoriesScreenUIState(
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

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
    public val screenBottomSheetType: CategoriesScreenBottomSheetType,
    public var categoryIdToDelete: Int?,
    public var clickedItemId: Int?,
    public val pagerState: PagerState,
    public val setScreenBottomSheetType: (CategoriesScreenBottomSheetType) -> Unit,
    public val setCategoryIdToDelete: (Int?) -> Unit,
    public val setClickedItemId: (Int?) -> Unit,
    public val isLoading: Boolean,
    public val selectedTabIndex: Int,
    public val validTransactionTypes: List<TransactionType>,
    public val tabData: List<MyTabData>,
    public val categoriesGridItemDataMap: Map<TransactionType, List<CategoriesGridItemData>>,
    public val resetScreenBottomSheetType: () -> Unit,
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

        CategoriesScreenUIState(
            screenBottomSheetType = screenBottomSheetType,
            categoryIdToDelete = categoryIdToDelete,
            clickedItemId = clickedItemId,
            pagerState = pagerState,
            setScreenBottomSheetType = setScreenBottomSheetType,
            setCategoryIdToDelete = setCategoryIdToDelete,
            setClickedItemId = setClickedItemId,
            isLoading = unwrappedData.isNull(),
            selectedTabIndex = unwrappedData?.selectedTabIndex.orZero(),
            validTransactionTypes = listOf(
                TransactionType.EXPENSE,
                TransactionType.INCOME,
                TransactionType.INVESTMENT,
            ),
            tabData = validTransactionTypes.map {
                MyTabData(
                    title = it.title,
                )
            },
            categoriesGridItemDataMap =
            unwrappedData?.categoriesGridItemDataMap.orEmpty(),
            resetScreenBottomSheetType = {
                setScreenBottomSheetType(CategoriesScreenBottomSheetType.None)
            },
        )
    }
}

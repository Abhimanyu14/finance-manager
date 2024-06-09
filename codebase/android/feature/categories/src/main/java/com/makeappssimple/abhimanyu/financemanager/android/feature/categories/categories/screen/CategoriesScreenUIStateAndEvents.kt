package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.screen

import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.tabrow.MyTabData
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateAndEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.griditem.CategoriesGridItemData
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Stable
internal class CategoriesScreenUIStateAndEvents(
    val state: CategoriesScreenUIState,
    val events: CategoriesScreenUIStateEvents,
) : ScreenUIStateAndEvents

@Composable
internal fun rememberCategoriesScreenUIStateAndEvents(
    categoriesGridItemDataMap: ImmutableMap<TransactionType, ImmutableList<CategoriesGridItemData>>,
): CategoriesScreenUIStateAndEvents {
    // region pager state
    val pagerState: PagerState = rememberPagerState(
        pageCount = { 3 },
    )
    // endregion

    // region screen bottom sheet type
    var screenBottomSheetType: CategoriesScreenBottomSheetType by remember {
        mutableStateOf(
            value = CategoriesScreenBottomSheetType.None,
        )
    }
    val setScreenBottomSheetType =
        { updatedCategoriesBottomSheetType: CategoriesScreenBottomSheetType ->
            screenBottomSheetType = updatedCategoriesBottomSheetType
        }
    val resetScreenBottomSheetType = {
        setScreenBottomSheetType(CategoriesScreenBottomSheetType.None)
    }
    // endregion

    // region category id to delete
    var categoryIdToDelete: Int? by remember {
        mutableStateOf(
            value = null,
        )
    }
    val setCategoryIdToDelete = { updatedCategoryIdToDelete: Int? ->
        categoryIdToDelete = updatedCategoryIdToDelete
    }
    // endregion

    // region clicked item id
    var clickedItemId: Int? by remember {
        mutableStateOf(
            value = null,
        )
    }
    val setClickedItemId = { updatedClickedItemId: Int? ->
        clickedItemId = updatedClickedItemId
    }
    // endregion

    // region selected category type index
    var selectedCategoryTypeIndex: Int by remember {
        mutableIntStateOf(
            value = 0,
        )
    }
    val setSelectedCategoryTypeIndex = { updatedSelectedCategoryTypeIndex: Int ->
        selectedCategoryTypeIndex = updatedSelectedCategoryTypeIndex
    }
    // endregion

    return remember(
        pagerState,
        screenBottomSheetType,
        setScreenBottomSheetType,
        categoryIdToDelete,
        setCategoryIdToDelete,
        clickedItemId,
        setClickedItemId,
        selectedCategoryTypeIndex,
        setSelectedCategoryTypeIndex,
        categoriesGridItemDataMap,
    ) {
        val validTransactionTypes = persistentListOf(
            TransactionType.EXPENSE,
            TransactionType.INCOME,
            TransactionType.INVESTMENT,
        )

        // TODO(Abhi): Can be reordered to match the class ordering
        CategoriesScreenUIStateAndEvents(
            state = CategoriesScreenUIState(
                isBottomSheetVisible = screenBottomSheetType != CategoriesScreenBottomSheetType.None,
                screenBottomSheetType = screenBottomSheetType,
                categoryIdToDelete = categoryIdToDelete,
                clickedItemId = clickedItemId,
                selectedTabIndex = selectedCategoryTypeIndex,
                tabData = validTransactionTypes.map {
                    MyTabData(
                        title = it.title,
                    )
                }.toImmutableList(),
                validTransactionTypes = validTransactionTypes,
                categoriesGridItemDataMap = categoriesGridItemDataMap,
                pagerState = pagerState,
            ),
            events = CategoriesScreenUIStateEvents(
                resetScreenBottomSheetType = resetScreenBottomSheetType,
                setCategoryIdToDelete = setCategoryIdToDelete,
                setClickedItemId = setClickedItemId,
                setScreenBottomSheetType = setScreenBottomSheetType,
                setSelectedCategoryTypeIndex = setSelectedCategoryTypeIndex,
            ),
        )
    }
}

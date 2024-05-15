package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.screen

import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.tabrow.MyTabData
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.griditem.CategoriesGridItemData
import kotlinx.collections.immutable.ImmutableList

@Stable
internal data class CategoriesScreenUIState(
    val isBottomSheetVisible: Boolean,
    val screenBottomSheetType: CategoriesScreenBottomSheetType,
    val categoryIdToDelete: Int?,
    val clickedItemId: Int?,
    val selectedTabIndex: Int,
    val tabData: ImmutableList<MyTabData>,
    val validTransactionTypes: ImmutableList<TransactionType>,
    val categoriesGridItemDataMap: Map<TransactionType, ImmutableList<CategoriesGridItemData>>,
    val pagerState: PagerState,
) : ScreenUIState

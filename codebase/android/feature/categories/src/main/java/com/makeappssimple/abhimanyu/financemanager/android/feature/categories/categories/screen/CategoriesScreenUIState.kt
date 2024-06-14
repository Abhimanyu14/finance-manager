package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.screen

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.tabrow.MyTabData
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.griditem.CategoriesGridItemData
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Stable
internal data class CategoriesScreenUIState(
    val isBottomSheetVisible: Boolean = false,
    val isLoading: Boolean = true,
    val screenBottomSheetType: CategoriesScreenBottomSheetType = CategoriesScreenBottomSheetType.None,
    val categoryIdToDelete: Int? = null,
    val clickedItemId: Int? = null,
    val selectedTabIndex: Int = 0,
    val tabData: ImmutableList<MyTabData> = persistentListOf(),
    val validTransactionTypes: ImmutableList<TransactionType> = persistentListOf(),
    val categoriesGridItemDataMap: Map<TransactionType, ImmutableList<CategoriesGridItemData>> = emptyMap(),
) : ScreenUIState

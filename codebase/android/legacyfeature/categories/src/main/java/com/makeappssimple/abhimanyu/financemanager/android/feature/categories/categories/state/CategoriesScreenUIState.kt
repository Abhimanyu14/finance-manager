package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.state

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.cre.designsystem.component.tabrow.MyTabData
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.griditem.CategoriesGridItemData
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.bottomsheet.CategoriesScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.snackbar.CategoriesScreenSnackbarType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Stable
internal data class CategoriesScreenUIState(
    val isBottomSheetVisible: Boolean = false,
    val isLoading: Boolean = true,
    val screenBottomSheetType: CategoriesScreenBottomSheetType = CategoriesScreenBottomSheetType.None,
    val screenSnackbarType: CategoriesScreenSnackbarType = CategoriesScreenSnackbarType.None,
    val categoryIdToDelete: Int? = null,
    val clickedItemId: Int? = null,
    val tabData: ImmutableList<MyTabData> = persistentListOf(),
    val validTransactionTypes: ImmutableList<TransactionType> = persistentListOf(),
    val categoriesGridItemDataMap: Map<TransactionType, ImmutableList<CategoriesGridItemData>> = emptyMap(),
) : ScreenUIState

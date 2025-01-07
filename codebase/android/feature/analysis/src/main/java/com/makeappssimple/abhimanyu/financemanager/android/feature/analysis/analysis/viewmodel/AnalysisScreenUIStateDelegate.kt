package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.analysis.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.cre.model.feature.analysis.Filter
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.analysis.bottomsheet.AnalysisScreenBottomSheetType
import kotlinx.coroutines.flow.MutableStateFlow

internal interface AnalysisScreenUIStateDelegate {
    // region UI state
    val isLoading: MutableStateFlow<Boolean>
    val selectedFilter: MutableStateFlow<Filter>
    val screenBottomSheetType: MutableStateFlow<AnalysisScreenBottomSheetType>
    val selectedTransactionTypeIndex: MutableStateFlow<Int>
    // endregion

    // region loading
    fun startLoading()

    fun completeLoading()

    fun <T> withLoading(
        block: () -> T,
    ): T

    suspend fun <T> withLoadingSuspend(
        block: suspend () -> T,
    ): T
    // endregion

    // region state events
    fun navigateUp()

    fun resetScreenBottomSheetType()

    fun setScreenBottomSheetType(
        updatedAnalysisScreenBottomSheetType: AnalysisScreenBottomSheetType,
    )

    fun setSelectedFilter(
        updatedSelectedFilter: Filter,
    )

    fun setSelectedTransactionTypeIndex(
        updatedSelectedTransactionTypeIndex: Int,
    )
    // endregion
}

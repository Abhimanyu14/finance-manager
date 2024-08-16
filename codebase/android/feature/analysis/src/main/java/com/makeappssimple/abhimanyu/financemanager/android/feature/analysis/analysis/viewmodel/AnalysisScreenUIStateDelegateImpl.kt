package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.analysis.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.analysis.Filter
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.analysis.bottomsheet.AnalysisScreenBottomSheetType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

internal class AnalysisScreenUIStateDelegateImpl(
    private val navigator: Navigator,
) : AnalysisScreenUIStateDelegate {
    // region UI state
    override val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(
        value = true,
    )
    override val selectedFilter: MutableStateFlow<Filter> = MutableStateFlow(
        value = Filter(),
    )
    override val screenBottomSheetType: MutableStateFlow<AnalysisScreenBottomSheetType> =
        MutableStateFlow(
            value = AnalysisScreenBottomSheetType.None,
        )
    override val selectedTransactionTypeIndex: MutableStateFlow<Int> = MutableStateFlow(
        value = 0,
    )
    // endregion

    // region loading
    override fun startLoading() {
        isLoading.update {
            true
        }
    }

    override fun completeLoading() {
        isLoading.update {
            false
        }
    }
    // endregion

    // region state events
    override fun navigateUp() {
        navigator.navigateUp()
    }

    override fun resetScreenBottomSheetType() {
        setScreenBottomSheetType(
            updatedAnalysisScreenBottomSheetType = AnalysisScreenBottomSheetType.None,
        )
    }

    override fun setScreenBottomSheetType(
        updatedAnalysisScreenBottomSheetType: AnalysisScreenBottomSheetType,
    ) {
        screenBottomSheetType.update {
            updatedAnalysisScreenBottomSheetType
        }
    }

    override fun setSelectedFilter(
        updatedSelectedFilter: Filter,
    ) {
        selectedFilter.update {
            updatedSelectedFilter
        }
    }

    override fun setSelectedTransactionTypeIndex(
        updatedSelectedTransactionTypeIndex: Int,
    ) {
        selectedTransactionTypeIndex.update {
            updatedSelectedTransactionTypeIndex
        }
    }
    // endregion
}

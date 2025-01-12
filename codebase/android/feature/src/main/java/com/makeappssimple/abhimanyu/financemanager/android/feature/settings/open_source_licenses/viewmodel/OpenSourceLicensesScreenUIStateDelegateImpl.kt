package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.bottomsheet.OpenSourceLicensesScreenBottomSheetType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

internal class OpenSourceLicensesScreenUIStateDelegateImpl(
    private val navigationKit: NavigationKit,
) : OpenSourceLicensesScreenUIStateDelegate {
    // region UI state
    override val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(
        value = true,
    )
    override val screenBottomSheetType: MutableStateFlow<OpenSourceLicensesScreenBottomSheetType> =
        MutableStateFlow(
            value = OpenSourceLicensesScreenBottomSheetType.None,
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

    override fun <T> withLoading(
        block: () -> T,
    ): T {
        startLoading()
        val result = block()
        completeLoading()
        return result
    }

    override suspend fun <T> withLoadingSuspend(
        block: suspend () -> T,
    ): T {
        startLoading()
        try {
            return block()
        } finally {
            completeLoading()
        }
    }
    // endregion

    // region state events
    override fun navigateUp() {
        navigationKit.navigateUp()
    }

    override fun resetScreenBottomSheetType() {
        updateScreenBottomSheetType(
            updatedOpenSourceLicensesScreenBottomSheetType = OpenSourceLicensesScreenBottomSheetType.None,
        )
    }

    override fun updateScreenBottomSheetType(
        updatedOpenSourceLicensesScreenBottomSheetType: OpenSourceLicensesScreenBottomSheetType,
    ) {
        screenBottomSheetType.update {
            updatedOpenSourceLicensesScreenBottomSheetType
        }
    }
    // endregion
}

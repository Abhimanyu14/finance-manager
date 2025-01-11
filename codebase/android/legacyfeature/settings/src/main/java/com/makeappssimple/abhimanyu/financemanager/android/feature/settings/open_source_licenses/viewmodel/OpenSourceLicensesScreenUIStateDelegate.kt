package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.bottomsheet.OpenSourceLicensesScreenBottomSheetType
import kotlinx.coroutines.flow.MutableStateFlow

internal interface OpenSourceLicensesScreenUIStateDelegate {
    // region UI state
    val isLoading: MutableStateFlow<Boolean>
    val screenBottomSheetType: MutableStateFlow<OpenSourceLicensesScreenBottomSheetType>
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
        updatedOpenSourceLicensesScreenBottomSheetType: OpenSourceLicensesScreenBottomSheetType,
    )
    // endregion
}

package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultObjectStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.MyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.MyNavigationDirections
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.screen.OpenSourceLicensesScreenUIData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.jetbrains.annotations.VisibleForTesting
import javax.inject.Inject

@HiltViewModel
internal class OpenSourceLicensesScreenViewModelImpl @Inject constructor(
    override val myLogger: MyLogger,
    @VisibleForTesting internal val navigationManager: NavigationManager,
) : OpenSourceLicensesScreenViewModel, ViewModel() {
    override val screenUIData: StateFlow<MyResult<OpenSourceLicensesScreenUIData>?> =
        MutableStateFlow(
            value = MyResult.Success(
                data = OpenSourceLicensesScreenUIData(),
            )
        ).defaultObjectStateIn(
            scope = viewModelScope,
        )

    override fun navigateUp() {
        navigationManager.navigate(
            MyNavigationDirections.NavigateUp
        )
    }
}
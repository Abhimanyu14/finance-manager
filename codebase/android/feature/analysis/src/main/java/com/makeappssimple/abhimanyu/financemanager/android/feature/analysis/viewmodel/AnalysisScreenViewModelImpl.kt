package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.viewmodel

import androidx.lifecycle.ViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.Logger
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.MyNavigationDirections
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class AnalysisScreenViewModelImpl @Inject constructor(
    override val logger: Logger,
    override val navigationManager: NavigationManager,
) : AnalysisScreenViewModel, ViewModel() {
    override fun navigateUp() {
        navigationManager.navigate(
            MyNavigationDirections.NavigateUp
        )
    }
}

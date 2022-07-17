package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.settings.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.data.usecase.BackupDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.usecase.RecalculateTotalUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.usecase.RestoreDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateUp
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class SettingsScreenViewModelImpl @Inject constructor(
    override val navigationManager: NavigationManager,
    private val dispatcherProvider: DispatcherProvider,
    private val backupDataUseCase: BackupDataUseCase,
    private val restoreDataUseCase: RestoreDataUseCase,
    private val recalculateTotalUseCase: RecalculateTotalUseCase,
) : SettingsScreenViewModel, ViewModel() {

    override fun trackScreen() {
        // TODO-Abhi: Add screen tracking code
    }

    override fun backupDataToDocument(
        uri: Uri,
    ) {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            backupDataUseCase(
                uri = uri,
            )
        }
    }

    override fun restoreDataFromDocument(
        uri: Uri,
    ) {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            restoreDataUseCase(
                uri = uri,
            )
        }
    }

    override fun recalculateTotal() {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            recalculateTotalUseCase()
            navigateUp(
                navigationManager = navigationManager,
            )
        }
    }
}

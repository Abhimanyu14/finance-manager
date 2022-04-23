package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.settings

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.data.usecase.BackupDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.usecase.RestoreDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModelImpl @Inject constructor(
    override val navigationManager: NavigationManager,
    private val dispatcherProvider: DispatcherProvider,
    private val backupDataUseCase: BackupDataUseCase,
    private val restoreDataUseCase: RestoreDataUseCase,
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
}

package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.settings

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.data.usecase.BackupDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.usecase.RestoreDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModelImpl @Inject constructor(
    override val navigationManager: NavigationManager,
    private val backupDataUseCase: BackupDataUseCase,
    private val restoreDataUseCase: RestoreDataUseCase,
) : SettingsViewModel, ViewModel() {

    override fun trackScreen() {
        // TODO-Abhi: Add screen tracking code
    }

    override fun backupDataToDocument(
        uri: Uri,
    ) {
        viewModelScope.launch(
            context = Dispatchers.IO,
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
            context = Dispatchers.IO,
        ) {
            restoreDataUseCase(
                uri = uri,
            )
        }
    }
}

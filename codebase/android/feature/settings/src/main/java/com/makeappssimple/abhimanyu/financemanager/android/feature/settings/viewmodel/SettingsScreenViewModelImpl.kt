package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.appversion.AppVersionUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultObjectStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.BackupDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.RecalculateTotalUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.RestoreDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.Logger
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.MyNavigationDirections
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.screen.SettingsScreenUIData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SettingsScreenViewModelImpl @Inject constructor(
    appVersionUtil: AppVersionUtil,
    override val logger: Logger,
    override val navigationManager: NavigationManager,
    private val backupDataUseCase: BackupDataUseCase,
    private val dispatcherProvider: DispatcherProvider,
    private val recalculateTotalUseCase: RecalculateTotalUseCase,
    private val restoreDataUseCase: RestoreDataUseCase,
) : SettingsScreenViewModel, ViewModel() {
    private val appVersionName: String = appVersionUtil.getAppVersion()?.versionName.orEmpty()
    override val screenUIData: StateFlow<SettingsScreenUIData?> = flow<SettingsScreenUIData> {
        SettingsScreenUIData(
            appVersion = appVersionName,
        )
    }.defaultObjectStateIn(
        scope = viewModelScope,
    )

    override fun backupDataToDocument(
        uri: Uri,
    ) {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            launch {
                backupDataUseCase(
                    uri = uri,
                )
            }
            navigationManager.navigate(
                navigationCommand = MyNavigationDirections.NavigateUp
            )
        }
    }

    override fun navigateToTransactionForValuesScreen() {
        navigationManager.navigate(
            MyNavigationDirections.TransactionForValues
        )
    }

    override fun navigateUp() {
        navigationManager.navigate(
            MyNavigationDirections.NavigateUp
        )
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
            navigationManager.navigate(
                navigationCommand = MyNavigationDirections.NavigateUp
            )
        }
    }

    override fun recalculateTotal() {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            recalculateTotalUseCase()
            navigationManager.navigate(
                navigationCommand = MyNavigationDirections.NavigateUp
            )
        }
    }
}

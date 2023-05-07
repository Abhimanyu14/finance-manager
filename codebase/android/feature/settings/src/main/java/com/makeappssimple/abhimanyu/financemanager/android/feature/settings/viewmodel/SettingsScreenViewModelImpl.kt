package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.appversion.AppVersionUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.RestoreDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.BackupDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.RecalculateTotalUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.Logger
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.MyNavigationDirections
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
internal class SettingsScreenViewModelImpl @Inject constructor(
    override val logger: Logger,
    override val navigationManager: NavigationManager,
    private val appVersionUtil: AppVersionUtil,
    private val backupDataUseCase: BackupDataUseCase,
    private val dispatcherProvider: DispatcherProvider,
    private val recalculateTotalUseCase: RecalculateTotalUseCase,
    private val restoreDataUseCase: RestoreDataUseCase,
) : SettingsScreenViewModel, ViewModel() {
    override fun backupDataToDocument(
        uri: Uri,
    ) {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            backupDataUseCase(
                uri = uri,
            )
            navigationManager.navigate(
                navigationCommand = MyNavigationDirections.NavigateUp
            )
        }
    }

    override fun getAppVersionName(): String {
        return appVersionUtil.getAppVersion()?.versionName.orEmpty()
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

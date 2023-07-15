package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.appversion.AppVersionUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.di.IoDispatcher
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultObjectStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.BackupDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.RecalculateTotalUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.RestoreDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.MyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.MyNavigationDirections
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.screen.SettingsScreenUIData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SettingsScreenViewModelImpl @Inject constructor(
    appVersionUtil: AppVersionUtil,
    override val myLogger: MyLogger,
    private val backupDataUseCase: BackupDataUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val navigationManager: NavigationManager,
    private val recalculateTotalUseCase: RecalculateTotalUseCase,
    private val restoreDataUseCase: RestoreDataUseCase,
) : SettingsScreenViewModel, ViewModel() {
    private val appVersionName: String = appVersionUtil.getAppVersion()?.versionName.orEmpty()
    override val screenUIData: StateFlow<MyResult<SettingsScreenUIData>?> = MutableStateFlow(
        value = MyResult.Success(
            data = SettingsScreenUIData(
                appVersion = appVersionName,
            ),
        )
    ).defaultObjectStateIn(
        scope = viewModelScope,
    )

    override fun backupDataToDocument(
        uri: Uri,
    ) {
        viewModelScope.launch(
            context = ioDispatcher,
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

    override fun navigateToCategoriesScreen() {
        navigationManager.navigate(
            MyNavigationDirections.Categories
        )
    }

    override fun navigateToSourcesScreen() {
        navigationManager.navigate(
            MyNavigationDirections.Sources
        )
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
            context = ioDispatcher,
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
            context = ioDispatcher,
        ) {
            recalculateTotalUseCase()
            navigationManager.navigate(
                navigationCommand = MyNavigationDirections.NavigateUp
            )
        }
    }
}

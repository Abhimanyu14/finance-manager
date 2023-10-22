package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.alarmkit.AlarmKit
import com.makeappssimple.abhimanyu.financemanager.android.core.common.appversion.AppVersionUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.di.IoDispatcher
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orFalse
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultObjectStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.data.preferences.repository.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.BackupDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.RecalculateTotalUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.RestoreDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.MyNavigationDirections
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.screen.SettingsScreenEvent
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.screen.SettingsScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.screen.SettingsScreenUIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.jetbrains.annotations.VisibleForTesting
import javax.inject.Inject

@HiltViewModel
internal class SettingsScreenViewModelImpl @Inject constructor(
    appVersionUtil: AppVersionUtil,
    myPreferencesRepository: MyPreferencesRepository,
    private val alarmKit: AlarmKit,
    private val backupDataUseCase: BackupDataUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @VisibleForTesting internal val navigationManager: NavigationManager,
    private val recalculateTotalUseCase: RecalculateTotalUseCase,
    private val restoreDataUseCase: RestoreDataUseCase,
) : SettingsScreenViewModel, ViewModel() {
    private val appVersionName: String = appVersionUtil.getAppVersion()?.versionName.orEmpty()
    private val reminder = myPreferencesRepository.getReminder()

    private val _event: MutableSharedFlow<SettingsScreenEvent> = MutableSharedFlow()
    override val event: SharedFlow<SettingsScreenEvent> = _event
    override val screenUIData: StateFlow<MyResult<SettingsScreenUIData>?> = reminder.map {
        MyResult.Success(
            data = SettingsScreenUIData(
                isReminderEnabled = it?.isEnabled.orFalse(),
                appVersion = appVersionName,
            ),
        )
    }.defaultObjectStateIn(
        scope = viewModelScope,
    )

    override fun backupDataToDocument(
        uri: Uri,
    ) {
        viewModelScope.launch(
            context = ioDispatcher,
        ) {
            launch(
                context = ioDispatcher,
            ) {
                backupDataUseCase(
                    uri = uri,
                )
            }
            navigationManager.navigate(
                navigationCommand = MyNavigationDirections.NavigateUp
            )
        }
    }

    override fun disableReminder() {
        alarmKit.disableReminder()
    }

    override fun enableReminder() {
        alarmKit.enableReminder()
    }

    override fun handleUIEvents(
        uiEvent: SettingsScreenUIEvent,
    ) {
        when (uiEvent) {
            SettingsScreenUIEvent.NavigateToAccountsScreen -> {
                navigateToAccountsScreen()
            }

            SettingsScreenUIEvent.NavigateToCategoriesScreen -> {
                navigateToCategoriesScreen()
            }

            SettingsScreenUIEvent.NavigateToOpenSourceLicensesScreen -> {
                navigateToOpenSourceLicensesScreen()
            }

            SettingsScreenUIEvent.NavigateToTransactionForValuesScreen -> {
                navigateToTransactionForValuesScreen()
            }

            SettingsScreenUIEvent.NavigateUp -> {
                navigateUp()
            }

            else -> {
                // Noop, should have been handled in Screen composable or invalid event
            }
        }
    }

    private fun navigateToCategoriesScreen() {
        navigationManager.navigate(
            MyNavigationDirections.Categories
        )
    }

    private fun navigateToAccountsScreen() {
        navigationManager.navigate(
            MyNavigationDirections.Accounts
        )
    }

    private fun navigateToOpenSourceLicensesScreen() {
        navigationManager.navigate(
            MyNavigationDirections.OpenSourceLicenses
        )
    }

    private fun navigateToTransactionForValuesScreen() {
        navigationManager.navigate(
            MyNavigationDirections.TransactionForValues
        )
    }

    private fun navigateUp() {
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
            if (
                restoreDataUseCase(
                    uri = uri,
                )
            ) {
                navigationManager.navigate(
                    navigationCommand = MyNavigationDirections.NavigateUp
                )
            } else {
                _event.emit(SettingsScreenEvent.RestoreDataFailed)
            }
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

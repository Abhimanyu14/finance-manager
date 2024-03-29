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
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.common.BackupDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.common.RecalculateTotalUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.common.RestoreDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.screen.SettingsScreenEvent
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.screen.SettingsScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.screen.SettingsScreenUIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
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
    @VisibleForTesting internal val navigator: Navigator,
    private val recalculateTotalUseCase: RecalculateTotalUseCase,
    private val restoreDataUseCase: RestoreDataUseCase,
) : SettingsScreenViewModel, ViewModel() {
    private val appVersionName: String = appVersionUtil.getAppVersion()?.versionName.orEmpty()
    private val reminder = myPreferencesRepository.getReminder()
    private val isLoading = MutableStateFlow(
        value = false,
    )

    private val _event: MutableSharedFlow<SettingsScreenEvent> = MutableSharedFlow()
    override val event: SharedFlow<SettingsScreenEvent> = _event
    override val screenUIData: StateFlow<MyResult<SettingsScreenUIData>?> = combine(
        isLoading,
        reminder,
    ) { isLoading, reminder ->
        MyResult.Success(
            data = SettingsScreenUIData(
                isReminderEnabled = reminder?.isEnabled.orFalse(),
                isLoading = isLoading,
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
            isLoading.value = true
            launch(
                context = ioDispatcher,
            ) {
                backupDataUseCase(
                    uri = uri,
                )
            }
            navigator.navigateUp()
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
        navigator.navigateToCategoriesScreen()
    }

    private fun navigateToAccountsScreen() {
        navigator.navigateToAccountsScreen()
    }

    private fun navigateToOpenSourceLicensesScreen() {
        navigator.navigateToOpenSourceLicensesScreen()
    }

    private fun navigateToTransactionForValuesScreen() {
        navigator.navigateToTransactionForValuesScreen()
    }

    private fun navigateUp() {
        navigator.navigateUp()
    }

    override fun restoreDataFromDocument(
        uri: Uri,
    ) {
        viewModelScope.launch(
            context = ioDispatcher,
        ) {
            isLoading.value = true
            delay(5000)
            if (
                restoreDataUseCase(
                    uri = uri,
                )
            ) {
                navigator.navigateUp()
            } else {
                isLoading.value = false
                _event.emit(SettingsScreenEvent.RestoreDataFailed)
            }
        }
    }

    override fun recalculateTotal() {
        viewModelScope.launch(
            context = ioDispatcher,
        ) {
            isLoading.value = true
            recalculateTotalUseCase()
            navigator.navigateUp()
        }
    }
}

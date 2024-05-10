package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.alarmkit.AlarmKit
import com.makeappssimple.abhimanyu.financemanager.android.core.common.appversion.AppVersionUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.CloseableCoroutineScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orFalse
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultObjectStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.common.BackupDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.common.RecalculateTotalUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.common.RestoreDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.screen.SettingsScreenEvent
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.screen.SettingsScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.screen.SettingsScreenUIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
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
public class SettingsScreenViewModel @Inject constructor(
    appVersionUtil: AppVersionUtil,
    myPreferencesRepository: MyPreferencesRepository,
    private val alarmKit: AlarmKit,
    private val backupDataUseCase: BackupDataUseCase,
    private val closeableCoroutineScope: CloseableCoroutineScope,
    private val dispatcherProvider: DispatcherProvider,
    @VisibleForTesting internal val navigator: Navigator,
    private val recalculateTotalUseCase: RecalculateTotalUseCase,
    private val restoreDataUseCase: RestoreDataUseCase,
) : ScreenViewModel, ViewModel(closeableCoroutineScope) {
    private val appVersionName: String = appVersionUtil.getAppVersion()?.versionName.orEmpty()
    private val reminder = myPreferencesRepository.getReminder()
    private val isLoading = MutableStateFlow(
        value = false,
    )

    private val _event: MutableSharedFlow<SettingsScreenEvent> = MutableSharedFlow()
    public val event: SharedFlow<SettingsScreenEvent> = _event
    public val screenUIData: StateFlow<MyResult<SettingsScreenUIData>?> = combine(
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
        scope = closeableCoroutineScope,
    )

    public fun backupDataToDocument(
        uri: Uri,
    ) {
        closeableCoroutineScope.launch(
            context = dispatcherProvider.io,
        ) {
            isLoading.value = true
            val isBackupSuccessful = async(
                context = dispatcherProvider.io,
            ) {
                backupDataUseCase(
                    uri = uri,
                )
            }.await()
            Log.e("Abhi", "$isBackupSuccessful")
            // TODO(Abhi): use the result to show snackbar to the user
            navigator.navigateUp()
        }
    }

    public fun disableReminder() {
        alarmKit.disableReminder()
    }

    public fun enableReminder() {
        alarmKit.enableReminder()
    }

    public fun handleUIEvent(
        uiEvent: SettingsScreenUIEvent,
    ) {
        when (uiEvent) {
            is SettingsScreenUIEvent.OnAccountsListItemClick -> {
                navigateToAccountsScreen()
            }

            is SettingsScreenUIEvent.OnCategoriesListItemClick -> {
                navigateToCategoriesScreen()
            }

            is SettingsScreenUIEvent.OnOpenSourceLicensesListItemClick -> {
                navigateToOpenSourceLicensesScreen()
            }

            is SettingsScreenUIEvent.OnRecalculateTotalListItemClick -> {
                recalculateTotal()
            }

            is SettingsScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                navigateUp()
            }

            is SettingsScreenUIEvent.OnTransactionForListItemClick -> {
                navigateToTransactionForValuesScreen()
            }

            else -> {
                // No-op, should have been handled in Screen composable or invalid event
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

    public fun restoreDataFromDocument(
        uri: Uri,
    ) {
        closeableCoroutineScope.launch(
            context = dispatcherProvider.io,
        ) {
            isLoading.value = true
            delay(5000)
            if (restoreDataUseCase(
                    uri = uri,
                )
            ) {
                navigator.navigateUp()
            } else {
                isLoading.value = false
                _event.emit(
                    value = SettingsScreenEvent.RestoreDataFailed,
                )
            }
        }
    }

    public fun recalculateTotal() {
        closeableCoroutineScope.launch(
            context = dispatcherProvider.io,
        ) {
            isLoading.value = true
            recalculateTotalUseCase()
            navigator.navigateUp()
        }
    }
}

package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.viewmodel

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.alarm.AlarmKit
import com.makeappssimple.abhimanyu.financemanager.android.core.common.appversion.AppVersionKit
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.di.ApplicationScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.combineAndCollectLatest
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orFalse
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.common.BackupDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.common.RecalculateTotalUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.common.RestoreDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LogKit
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.bottomsheet.SettingsScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.snackbar.SettingsScreenSnackbarType
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.state.SettingsScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.state.SettingsScreenUIStateEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.annotations.VisibleForTesting
import javax.inject.Inject

@HiltViewModel
internal class SettingsScreenViewModel @Inject constructor(
    @ApplicationScope coroutineScope: CoroutineScope,
    private val alarmKit: AlarmKit,
    private val appVersionKit: AppVersionKit,
    private val backupDataUseCase: BackupDataUseCase,
    private val myPreferencesRepository: MyPreferencesRepository,
    private val recalculateTotalUseCase: RecalculateTotalUseCase,
    private val restoreDataUseCase: RestoreDataUseCase,
    internal val logKit: LogKit,
    @VisibleForTesting internal val navigationKit: NavigationKit,
) : ScreenViewModel(
    viewModelScope = coroutineScope,
), SettingsScreenUIStateDelegate by SettingsScreenUIStateDelegateImpl(
    alarmKit = alarmKit,
    coroutineScope = coroutineScope,
    navigationKit = navigationKit,
    recalculateTotalUseCase = recalculateTotalUseCase,
) {
    // region initial data
    private var appVersion: String = ""
    private val isReminderEnabled: MutableStateFlow<Boolean> = MutableStateFlow(
        value = false,
    )
    // endregion

    // region uiStateAndStateEvents
    internal val uiState: MutableStateFlow<SettingsScreenUIState> =
        MutableStateFlow(
            value = SettingsScreenUIState(),
        )
    internal val uiStateEvents: SettingsScreenUIStateEvents = SettingsScreenUIStateEvents(
        disableReminder = ::disableReminder,
        enableReminder = ::enableReminder,
        navigateToAccountsScreen = ::navigateToAccountsScreen,
        navigateToCategoriesScreen = ::navigateToCategoriesScreen,
        navigateToOpenSourceLicensesScreen = ::navigateToOpenSourceLicensesScreen,
        navigateToTransactionForValuesScreen = ::navigateToTransactionForValuesScreen,
        navigateUp = ::navigateUp,
        recalculateTotal = ::recalculateTotal,
        resetScreenBottomSheetType = ::resetScreenBottomSheetType,
        resetScreenSnackbarType = ::resetScreenSnackbarType,
        setScreenBottomSheetType = ::updateScreenBottomSheetType,
        setScreenSnackbarType = ::updateScreenSnackbarType,
    )
    // endregion

    // region initViewModel
    internal fun initViewModel() {
        observeData()
        fetchData()
    }

    private fun fetchData() {
        getAppVersion()
    }

    private fun observeData() {
        observeForUiStateAndStateEvents()
        observeForReminder()
    }
    // endregion

    // region backupDataToDocument
    internal fun backupDataToDocument(
        uri: Uri,
    ) {
        viewModelScope.launch {
            startLoading()
            val isBackupSuccessful = backupDataUseCase(
                uri = uri,
            )
            if (isBackupSuccessful) {
                navigationKit.navigateUp()
            } else {
                // TODO(Abhi): use the result to show snackbar to the user
            }
        }
    }
    // endregion

    // region restoreDataFromDocument
    internal fun restoreDataFromDocument(
        uri: Uri,
    ) {
        viewModelScope.launch {
            startLoading()
            if (restoreDataUseCase(
                    uri = uri,
                )
            ) {
                navigationKit.navigateUp()
            } else {
                completeLoading()
                updateScreenSnackbarType(
                    updatedSettingsScreenSnackbarType = SettingsScreenSnackbarType.RestoreDataFailed,
                )
            }
        }
    }
    // endregion

    // region getAppVersion
    private fun getAppVersion() {
        appVersion = appVersionKit.getAppVersion()?.versionName.orEmpty()
    }
    // endregion

    // region observeForUiStateAndStateEvents
    private fun observeForUiStateAndStateEvents() {
        viewModelScope.launch {
            combineAndCollectLatest(
                isLoading,
                screenBottomSheetType,
                screenSnackbarType,
                isReminderEnabled,
            ) {
                    (
                        isLoading,
                        screenBottomSheetType,
                        screenSnackbarType,
                        isReminderEnabled,
                    ),
                ->
                uiState.update {
                    SettingsScreenUIState(
                        isBottomSheetVisible = screenBottomSheetType != SettingsScreenBottomSheetType.None,
                        isLoading = isLoading,
                        isReminderEnabled = isReminderEnabled,
                        screenBottomSheetType = screenBottomSheetType,
                        screenSnackbarType = screenSnackbarType,
                        appVersion = appVersion,
                    )
                }
            }
        }
    }
    // endregion

    // region observeForReminder
    private fun observeForReminder() {
        viewModelScope.launch {
            myPreferencesRepository.getReminderFlow().collectLatest { updatedReminder ->
                startLoading()
                isReminderEnabled.update {
                    updatedReminder?.isEnabled.orFalse()
                }
                completeLoading()
            }
        }
    }
    // endregion
}

package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.alarmkit.AlarmKit
import com.makeappssimple.abhimanyu.financemanager.android.core.common.appversion.AppVersionUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.combine
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orFalse
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultObjectStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.common.BackupDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.common.RecalculateTotalUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.common.RestoreDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Reminder
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.bottomsheet.SettingsScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.screen.SettingsScreenEvent
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.state.SettingsScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.state.SettingsScreenUIStateAndStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.state.SettingsScreenUIStateEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.annotations.VisibleForTesting
import javax.inject.Inject

@HiltViewModel
public class SettingsScreenViewModel @Inject constructor(
    myPreferencesRepository: MyPreferencesRepository,
    private val alarmKit: AlarmKit,
    private val appVersionUtil: AppVersionUtil,
    private val backupDataUseCase: BackupDataUseCase,
    @VisibleForTesting internal val navigator: Navigator,
    private val recalculateTotalUseCase: RecalculateTotalUseCase,
    private val restoreDataUseCase: RestoreDataUseCase,
) : ScreenViewModel, ViewModel() {
    private var appVersion: String = ""
    private val reminder: StateFlow<Reminder?> = myPreferencesRepository.getReminderFlow()
        .defaultObjectStateIn(
            scope = viewModelScope,
        )

    // region UI data
    private val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(
        value = true,
    )
    private val screenBottomSheetType: MutableStateFlow<SettingsScreenBottomSheetType> =
        MutableStateFlow(
            value = SettingsScreenBottomSheetType.None,
        )
    // endregion

    internal val uiStateAndStateEvents: MutableStateFlow<SettingsScreenUIStateAndStateEvents> =
        MutableStateFlow(
            value = SettingsScreenUIStateAndStateEvents(),
        )

    private val _event: MutableSharedFlow<SettingsScreenEvent> = MutableSharedFlow()
    internal val event: SharedFlow<SettingsScreenEvent> = _event

    internal fun initViewModel() {
        fetchData()
        observeData()
    }

    internal fun backupDataToDocument(
        uri: Uri,
    ) {
        viewModelScope.launch {
            isLoading.value = true
            val isBackupSuccessful = async {
                backupDataUseCase(
                    uri = uri,
                )
            }.await()
            Log.e("Abhi", "$isBackupSuccessful")
            // TODO(Abhi): use the result to show snackbar to the user
            navigator.navigateUp()
        }
    }

    internal fun restoreDataFromDocument(
        uri: Uri,
    ) {
        viewModelScope.launch {
            isLoading.value = true
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

    private fun fetchData() {
        appVersion = appVersionUtil.getAppVersion()?.versionName.orEmpty()
        viewModelScope.launch {
            isLoading.update {
                false
            }
        }
    }

    private fun observeData() {
        observeForUiStateAndStateEventsChanges()
    }

    private fun observeForUiStateAndStateEventsChanges() {
        viewModelScope.launch {
            combine(
                isLoading,
                screenBottomSheetType,
                reminder,
            ) {
                    isLoading,
                    screenBottomSheetType,
                    reminder,
                ->
                Triple(
                    isLoading,
                    screenBottomSheetType,
                    reminder,
                )
            }.collectLatest {
                    (
                        isLoading,
                        screenBottomSheetType,
                        reminder,
                    ),
                ->
                uiStateAndStateEvents.update {
                    SettingsScreenUIStateAndStateEvents(
                        state = SettingsScreenUIState(
                            isBottomSheetVisible = screenBottomSheetType != SettingsScreenBottomSheetType.None,
                            isLoading = isLoading,
                            isReminderEnabled = reminder?.isEnabled.orFalse(),
                            screenBottomSheetType = screenBottomSheetType,
                            appVersion = appVersion,
                        ),
                        events = SettingsScreenUIStateEvents(
                            disableReminder = ::disableReminder,
                            enableReminder = ::enableReminder,
                            navigateToAccountsScreen = ::navigateToAccountsScreen,
                            navigateToCategoriesScreen = ::navigateToCategoriesScreen,
                            navigateToOpenSourceLicensesScreen = ::navigateToOpenSourceLicensesScreen,
                            navigateToTransactionForValuesScreen = ::navigateToTransactionForValuesScreen,
                            navigateUp = ::navigateUp,
                            recalculateTotal = ::recalculateTotal,
                            resetScreenBottomSheetType = ::resetScreenBottomSheetType,
                            setScreenBottomSheetType = ::setScreenBottomSheetType,
                        ),
                    )
                }
            }
        }
    }

    // region state events
    private fun disableReminder() {
        alarmKit.disableReminder()
    }

    public fun enableReminder() {
        alarmKit.enableReminder()
    }

    private fun navigateToAccountsScreen() {
        navigator.navigateToAccountsScreen()
    }

    private fun navigateToCategoriesScreen() {
        navigator.navigateToCategoriesScreen()
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

    private fun recalculateTotal() {
        viewModelScope.launch {
            isLoading.value = true
            recalculateTotalUseCase()
            navigator.navigateUp()
        }
    }

    private fun resetScreenBottomSheetType() {
        setScreenBottomSheetType(
            updatedAnalysisScreenBottomSheetType = SettingsScreenBottomSheetType.None,
        )
    }

    private fun setScreenBottomSheetType(
        updatedAnalysisScreenBottomSheetType: SettingsScreenBottomSheetType,
    ) {
        screenBottomSheetType.update {
            updatedAnalysisScreenBottomSheetType
        }
    }
    // endregion
}

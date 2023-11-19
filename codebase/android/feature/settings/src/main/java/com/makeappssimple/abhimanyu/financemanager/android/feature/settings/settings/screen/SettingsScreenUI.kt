package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.screen

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.TextSnippet
import androidx.compose.material.icons.rounded.AccountBalance
import androidx.compose.material.icons.rounded.Backup
import androidx.compose.material.icons.rounded.Calculate
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.Groups
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Restore
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_CONTENT_SETTINGS
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_SETTINGS
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orFalse
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyLinearProgressIndicator
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.NavigationBarsAndImeSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.component.listitem.SettingsListItem
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.component.listitem.SettingsListItemData
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.component.listitem.SettingsListItemEvents

@Immutable
data class SettingsScreenListItemData(
    val data: SettingsListItemData,
    val events: SettingsListItemEvents = SettingsListItemEvents(),
)

@Composable
internal fun SettingsScreenUI(
    uiState: SettingsScreenUIState,
    state: CommonScreenUIState = rememberCommonScreenUIState(),
    handleUIEvents: (uiEvent: SettingsScreenUIEvent) -> Unit = {},
) {
    val context = LocalContext.current
    val listItemsData: List<SettingsScreenListItemData> = listOf(
        SettingsScreenListItemData(
            data = SettingsListItemData(
                isEnabled = uiState.isLoading,
                isHeading = true,
                textStringResourceId = R.string.screen_settings_data,
            ),
        ),
        SettingsScreenListItemData(
            data = SettingsListItemData(
                isEnabled = uiState.isLoading,
                imageVector = Icons.Rounded.Category,
                textStringResourceId = R.string.screen_settings_categories,
            ),
            events = SettingsListItemEvents(
                onClick = {
                    handleUIEvents(SettingsScreenUIEvent.NavigateToCategoriesScreen)
                },
            ),
        ),
        SettingsScreenListItemData(
            data = SettingsListItemData(
                isEnabled = uiState.isLoading,
                imageVector = Icons.Rounded.AccountBalance,
                textStringResourceId = R.string.screen_settings_accounts,
            ),
            events = SettingsListItemEvents(
                onClick = {
                    handleUIEvents(SettingsScreenUIEvent.NavigateToAccountsScreen)
                },
            ),
        ),
        SettingsScreenListItemData(
            data = SettingsListItemData(
                hasDivider = true,
                isEnabled = uiState.isLoading,
                imageVector = Icons.Rounded.Groups,
                textStringResourceId = R.string.screen_settings_transaction_for,
            ),
            events = SettingsListItemEvents(
                onClick = {
                    handleUIEvents(SettingsScreenUIEvent.NavigateToTransactionForValuesScreen)
                },
            ),
        ),
        SettingsScreenListItemData(
            data = SettingsListItemData(
                isEnabled = uiState.isLoading,
                isHeading = true,
                textStringResourceId = R.string.screen_settings_backup_and_restore,
            ),
        ),
        SettingsScreenListItemData(
            data = SettingsListItemData(
                isEnabled = uiState.isLoading,
                imageVector = Icons.Rounded.Backup,
                textStringResourceId = R.string.screen_settings_backup,
            ),
            events = SettingsListItemEvents(
                onClick = {
                    handleUIEvents(SettingsScreenUIEvent.BackupData)
                },
            ),
        ),
        SettingsScreenListItemData(
            data = SettingsListItemData(
                isEnabled = uiState.isLoading,
                imageVector = Icons.Rounded.Restore,
                textStringResourceId = R.string.screen_settings_restore,
            ),
            events = SettingsListItemEvents(
                onClick = {
                    handleUIEvents(SettingsScreenUIEvent.RestoreData)
                },
            ),
        ),
        SettingsScreenListItemData(
            data = SettingsListItemData(
                hasDivider = true,
                isEnabled = uiState.isLoading,
                imageVector = Icons.Rounded.Calculate,
                textStringResourceId = R.string.screen_settings_recalculate_total,
            ),
            events = SettingsListItemEvents(
                onClick = {
                    handleUIEvents(SettingsScreenUIEvent.RecalculateTotal)
                },
            ),
        ),
        SettingsScreenListItemData(
            data = SettingsListItemData(
                isEnabled = uiState.isLoading,
                isHeading = true,
                textStringResourceId = R.string.screen_settings_notifications,
            ),
        ),
        SettingsScreenListItemData(
            data = SettingsListItemData(
                hasDivider = true,
                isChecked = uiState.isReminderEnabled.orFalse(),
                isEnabled = uiState.isLoading,
                imageVector = Icons.Rounded.Notifications,
                textStringResourceId = R.string.screen_settings_reminder,
            ),
            events = SettingsListItemEvents(
                onClick = {
                    handleUIEvents(SettingsScreenUIEvent.ToggleReminder)
                },
                onCheckedChange = {
                    handleUIEvents(SettingsScreenUIEvent.ToggleReminder)
                },
            ),
        ),
        SettingsScreenListItemData(
            data = SettingsListItemData(
                isEnabled = uiState.isLoading,
                isHeading = true,
                textStringResourceId = R.string.screen_settings_about,
            ),
        ),
        SettingsScreenListItemData(
            data = SettingsListItemData(
                isEnabled = uiState.isLoading,
                imageVector = Icons.AutoMirrored.Rounded.TextSnippet,
                textStringResourceId = R.string.screen_settings_credits,
            ),
            events = SettingsListItemEvents(
                onClick = {
                    Toast.makeText(context, "Not Yet Implemented", Toast.LENGTH_SHORT).show()
                },
            ),
        ),
        SettingsScreenListItemData(
            data = SettingsListItemData(
                hasDivider = true,
                isEnabled = uiState.isLoading,
                imageVector = Icons.AutoMirrored.Rounded.TextSnippet,
                textStringResourceId = R.string.screen_settings_open_source_licenses,
            ),
            events = SettingsListItemEvents(
                onClick = {
                    handleUIEvents(SettingsScreenUIEvent.NavigateToOpenSourceLicensesScreen)
                },
            ),
        ),
    )

    MyScaffold(
        modifier = Modifier
            .testTag(SCREEN_SETTINGS)
            .fillMaxSize(),
        sheetContent = {
            when (uiState.screenBottomSheetType) {
                SettingsScreenBottomSheetType.NONE -> {
                    VerticalSpacer()
                }
            }
        },
        sheetState = state.modalBottomSheetState,
        snackbarHostState = uiState.snackbarHostState,
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.screen_settings_appbar_title,
                navigationAction = {
                    handleUIEvents(SettingsScreenUIEvent.NavigateUp)
                },
            )
        },
        onClick = {
            state.focusManager.clearFocus()
        },
        coroutineScope = state.coroutineScope,
        onBackPress = uiState.resetScreenBottomSheetType,
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .testTag(SCREEN_CONTENT_SETTINGS)
                .fillMaxWidth(),
        ) {
            // TODO(Abhi): Check why AnimatedVisibility is not working
            if (uiState.isLoading) {
                item {
                    MyLinearProgressIndicator(
                        modifier = Modifier
                            .testTag(
                                tag = stringResource(
                                    id = R.string.screen_settings_linear_progress_indicator_test_tag,
                                ),
                            ),
                    )
                }
            }
            itemsIndexed(
                items = listItemsData,
                key = { index, listItem ->
                    listItem.hashCode()
                },
            ) { index, it ->
                SettingsListItem(
                    Modifier.testTag("Item $index"),
                    data = it.data,
                    events = it.events,
                )
            }
            item {
                uiState.appVersion?.let {
                    MyText(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 16.dp,
                                top = 16.dp,
                                end = 16.dp,
                                bottom = 8.dp,
                            ),
                        text = stringResource(
                            id = R.string.screen_settings_app_version,
                            it,
                        ),
                        style = MaterialTheme.typography.headlineLarge
                            .copy(
                                color = MaterialTheme.colorScheme.primary,
                                textAlign = TextAlign.Center,
                            ),
                    )
                }
            }
            item {
                NavigationBarsAndImeSpacer()
            }
        }
    }
}

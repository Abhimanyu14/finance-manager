package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.screen

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_CONTENT_SETTINGS
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_SETTINGS
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orFalse
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyLinearProgressIndicator
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.NavigationBarsAndImeSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.settings.SettingsListItemAppVersion
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.settings.SettingsListItemAppVersionData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.settings.SettingsListItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.settings.SettingsListItemDivider
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.settings.SettingsListItemDividerData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.settings.SettingsListItemMenu
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.settings.SettingsListItemMenuData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.settings.SettingsListItemMenuEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.settings.SettingsListItemMenuSectionTitle
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.settings.SettingsListItemMenuSectionTitleData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.icons.MyIcons
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.R

@Immutable
data class SettingsScreenListItemData(
    val data: SettingsListItemData,
    val events: SettingsListItemMenuEvents = SettingsListItemMenuEvents(),
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
            data = SettingsListItemMenuSectionTitleData(
                textStringResourceId = R.string.screen_settings_data,
            ),
        ),
        SettingsScreenListItemData(
            data = SettingsListItemMenuData(
                isEnabled = uiState.isLoading,
                imageVector = MyIcons.Category,
                textStringResourceId = R.string.screen_settings_categories,
            ),
            events = SettingsListItemMenuEvents(
                onClick = {
                    handleUIEvents(SettingsScreenUIEvent.NavigateToCategoriesScreen)
                },
            ),
        ),
        SettingsScreenListItemData(
            data = SettingsListItemMenuData(
                isEnabled = uiState.isLoading,
                imageVector = MyIcons.AccountBalance,
                textStringResourceId = R.string.screen_settings_accounts,
            ),
            events = SettingsListItemMenuEvents(
                onClick = {
                    handleUIEvents(SettingsScreenUIEvent.NavigateToAccountsScreen)
                },
            ),
        ),
        SettingsScreenListItemData(
            data = SettingsListItemMenuData(
                isEnabled = uiState.isLoading,
                imageVector = MyIcons.Groups,
                textStringResourceId = R.string.screen_settings_transaction_for,
            ),
            events = SettingsListItemMenuEvents(
                onClick = {
                    handleUIEvents(SettingsScreenUIEvent.NavigateToTransactionForValuesScreen)
                },
            ),
        ),
        SettingsScreenListItemData(
            data = SettingsListItemDividerData(),
        ),
        SettingsScreenListItemData(
            data = SettingsListItemMenuSectionTitleData(
                textStringResourceId = R.string.screen_settings_backup_and_restore,
            ),
        ),
        SettingsScreenListItemData(
            data = SettingsListItemMenuData(
                isEnabled = uiState.isLoading,
                imageVector = MyIcons.Backup,
                textStringResourceId = R.string.screen_settings_backup,
            ),
            events = SettingsListItemMenuEvents(
                onClick = {
                    handleUIEvents(SettingsScreenUIEvent.BackupData)
                },
            ),
        ),
        SettingsScreenListItemData(
            data = SettingsListItemMenuData(
                isEnabled = uiState.isLoading,
                imageVector = MyIcons.Restore,
                textStringResourceId = R.string.screen_settings_restore,
            ),
            events = SettingsListItemMenuEvents(
                onClick = {
                    handleUIEvents(SettingsScreenUIEvent.RestoreData)
                },
            ),
        ),
        SettingsScreenListItemData(
            data = SettingsListItemMenuData(
                isEnabled = uiState.isLoading,
                imageVector = MyIcons.Calculate,
                textStringResourceId = R.string.screen_settings_recalculate_total,
            ),
            events = SettingsListItemMenuEvents(
                onClick = {
                    handleUIEvents(SettingsScreenUIEvent.RecalculateTotal)
                },
            ),
        ),
        SettingsScreenListItemData(
            data = SettingsListItemDividerData(),
        ),
        SettingsScreenListItemData(
            data = SettingsListItemMenuSectionTitleData(
                textStringResourceId = R.string.screen_settings_notifications,
            ),
        ),
        SettingsScreenListItemData(
            data = SettingsListItemMenuData(
                isChecked = uiState.isReminderEnabled.orFalse(),
                isEnabled = uiState.isLoading,
                imageVector = MyIcons.Notifications,
                textStringResourceId = R.string.screen_settings_reminder,
            ),
            events = SettingsListItemMenuEvents(
                onClick = {
                    handleUIEvents(SettingsScreenUIEvent.ToggleReminder)
                },
                onCheckedChange = {
                    handleUIEvents(SettingsScreenUIEvent.ToggleReminder)
                },
            ),
        ),
        SettingsScreenListItemData(
            data = SettingsListItemDividerData(),
        ),
        SettingsScreenListItemData(
            data = SettingsListItemMenuSectionTitleData(
                textStringResourceId = R.string.screen_settings_about,
            ),
        ),
        SettingsScreenListItemData(
            data = SettingsListItemMenuData(
                isEnabled = uiState.isLoading,
                imageVector = MyIcons.TextSnippet,
                textStringResourceId = R.string.screen_settings_credits,
            ),
            events = SettingsListItemMenuEvents(
                onClick = {
                    Toast.makeText(context, "Not Yet Implemented", Toast.LENGTH_SHORT).show()
                },
            ),
        ),
        SettingsScreenListItemData(
            data = SettingsListItemMenuData(
                isEnabled = uiState.isLoading,
                imageVector = MyIcons.TextSnippet,
                textStringResourceId = R.string.screen_settings_open_source_licenses,
            ),
            events = SettingsListItemMenuEvents(
                onClick = {
                    handleUIEvents(SettingsScreenUIEvent.NavigateToOpenSourceLicensesScreen)
                },
            ),
        ),
        SettingsScreenListItemData(
            data = SettingsListItemDividerData(),
        ),
        SettingsScreenListItemData(
            data = SettingsListItemAppVersionData(
                appVersionText = stringResource(
                    id = R.string.screen_settings_app_version,
                    uiState.appVersion.orEmpty(),
                ),
            ),
        ),
    )

    MyScaffold(
        modifier = Modifier
            .testTag(
                tag = SCREEN_SETTINGS,
            )
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
        onClick = state.focusManager::clearFocus,
        coroutineScope = state.coroutineScope,
        onBackPress = uiState.resetScreenBottomSheetType,
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .testTag(
                    tag = SCREEN_CONTENT_SETTINGS,
                )
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
                    when (listItem.data) {
                        is SettingsListItemAppVersionData -> {
                            "${listItem.data.contentType.title}_$index"
                        }

                        is SettingsListItemDividerData -> {
                            "${listItem.data.contentType.title}_$index"
                        }

                        is SettingsListItemMenuData -> {
                            "${listItem.data.textStringResourceId}"
                        }

                        is SettingsListItemMenuSectionTitleData -> {
                            "${listItem.data.textStringResourceId}"
                        }
                    }
                },
            ) { index, it ->
                when (it.data) {
                    is SettingsListItemAppVersionData -> {
                        uiState.appVersion?.let {
                            SettingsListItemAppVersion(
                                data = SettingsListItemAppVersionData(
                                    appVersionText = stringResource(
                                        id = R.string.screen_settings_app_version,
                                        it,
                                    ),
                                ),
                            )
                        }
                    }

                    is SettingsListItemDividerData -> {
                        SettingsListItemDivider(
                            modifier = Modifier
                                .testTag(
                                    tag = "Item $index",
                                ),
                        )
                    }

                    is SettingsListItemMenuData -> {
                        SettingsListItemMenu(
                            modifier = Modifier
                                .testTag(
                                    tag = "Item $index",
                                ),
                            data = it.data,
                            events = it.events,
                        )
                    }

                    is SettingsListItemMenuSectionTitleData -> {
                        SettingsListItemMenuSectionTitle(
                            modifier = Modifier
                                .testTag(
                                    tag = "Item $index",
                                ),
                            data = it.data,
                        )
                    }
                }
            }
            item {
                NavigationBarsAndImeSpacer()
            }
        }
    }
}

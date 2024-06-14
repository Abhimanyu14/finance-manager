@file:Suppress("StringLiteralDuplication")
// TODO(Abhi): To Fix - StringLiteralDuplication

package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.screen

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.icons.MyIcons
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.screen.CommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.screen.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.settings.SettingsListItemAppVersion
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.settings.SettingsListItemAppVersionData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.settings.SettingsListItemContent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.settings.SettingsListItemContentData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.settings.SettingsListItemContentEvent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.settings.SettingsListItemDivider
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.settings.SettingsListItemDividerData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.settings.SettingsListItemHeader
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.settings.SettingsListItemHeaderData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.top_app_bar.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.bottomsheet.SettingsScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.components.SettingsScreenListItemData
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.event.SettingsScreenUIEvent
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.state.SettingsScreenUIState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun SettingsScreenUI(
    uiState: SettingsScreenUIState,
    state: CommonScreenUIState = rememberCommonScreenUIState(),
    handleUIEvent: (uiEvent: SettingsScreenUIEvent) -> Unit = {},
) {
    val snackbarHostState: SnackbarHostState = remember {
        SnackbarHostState()
    }
    val settingsScreenListItemData: ImmutableList<SettingsScreenListItemData> =
        getSettingsListItemData(
            uiState = uiState,
            handleUIEvent = handleUIEvent,
        )

    MyScaffold(
        modifier = Modifier
            .testTag(
                tag = SCREEN_SETTINGS,
            )
            .fillMaxSize(),
        sheetContent = {
            when (uiState.screenBottomSheetType) {
                is SettingsScreenBottomSheetType.None -> {
                    VerticalSpacer()
                }
            }
        },
        sheetState = state.modalBottomSheetState,
        snackbarHostState = snackbarHostState,
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.screen_settings_appbar_title,
                navigationAction = {
                    handleUIEvent(SettingsScreenUIEvent.OnTopAppBarNavigationButtonClick)
                },
            )
        },
        onClick = state.focusManager::clearFocus,
        coroutineScope = state.coroutineScope,
        onNavigationBackButtonClick = {
            handleUIEvent(SettingsScreenUIEvent.OnNavigationBackButtonClick)
        },
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .testTag(
                    tag = SCREEN_CONTENT_SETTINGS,
                )
                .fillMaxWidth()
                .verticalScroll(
                    state = rememberScrollState(),
                ),
        ) {
            SettingsScreenLoader(
                isLoading = uiState.isLoading,
            )
            SettingsScreenContent(
                settingsScreenListItemData = settingsScreenListItemData,
            )
            NavigationBarsAndImeSpacer()
        }
    }
}

@Composable
private fun SettingsScreenLoader(
    isLoading: Boolean,
) {
    AnimatedVisibility(
        visible = isLoading,
    ) {
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

@Composable
private fun SettingsScreenContent(
    settingsScreenListItemData: ImmutableList<SettingsScreenListItemData>,
) {
    settingsScreenListItemData.mapIndexed { index, listItemData ->
        when (listItemData.data) {
            is SettingsListItemAppVersionData -> {
                SettingsListItemAppVersion(
                    data = SettingsListItemAppVersionData(
                        appVersionText = stringResource(
                            id = R.string.screen_settings_app_version,
                            listItemData.data.appVersionText,
                        ),
                    ),
                )
            }

            is SettingsListItemDividerData -> {
                SettingsListItemDivider(
                    modifier = Modifier
                        .testTag(
                            tag = "Item $index",
                        ),
                )
            }

            is SettingsListItemContentData -> {
                SettingsListItemContent(
                    modifier = Modifier
                        .testTag(
                            tag = "Item $index",
                        ),
                    data = listItemData.data,
                    handleEvent = listItemData.handleEvent,
                )
            }

            is SettingsListItemHeaderData -> {
                SettingsListItemHeader(
                    modifier = Modifier
                        .testTag(
                            tag = "Item $index",
                        ),
                    data = listItemData.data,
                )
            }
        }
    }
}

@Composable
private fun getSettingsListItemData(
    uiState: SettingsScreenUIState,
    handleUIEvent: (uiEvent: SettingsScreenUIEvent) -> Unit,
): ImmutableList<SettingsScreenListItemData> {
    val context = LocalContext.current
    return persistentListOf(
        SettingsScreenListItemData(
            data = SettingsListItemHeaderData(
                textStringResourceId = R.string.screen_settings_data,
            ),
        ),
        SettingsScreenListItemData(
            data = SettingsListItemContentData(
                isEnabled = !uiState.isLoading,
                imageVector = MyIcons.Category,
                textStringResourceId = R.string.screen_settings_categories,
            ),
            handleEvent = { event ->
                when (event) {
                    is SettingsListItemContentEvent.OnClick -> {
                        handleUIEvent(SettingsScreenUIEvent.OnCategoriesListItemClick)
                    }

                    else -> {
                        // No-op
                    }
                }
            },
        ),
        SettingsScreenListItemData(
            data = SettingsListItemContentData(
                isEnabled = !uiState.isLoading,
                imageVector = MyIcons.AccountBalance,
                textStringResourceId = R.string.screen_settings_accounts,
            ),
            handleEvent = { event ->
                when (event) {
                    is SettingsListItemContentEvent.OnClick -> {
                        handleUIEvent(SettingsScreenUIEvent.OnAccountsListItemClick)
                    }

                    else -> {
                        // No-op
                    }
                }
            },
        ),
        SettingsScreenListItemData(
            data = SettingsListItemContentData(
                isEnabled = !uiState.isLoading,
                imageVector = MyIcons.Groups,
                textStringResourceId = R.string.screen_settings_transaction_for,
            ),
            handleEvent = { event ->
                when (event) {
                    is SettingsListItemContentEvent.OnClick -> {
                        handleUIEvent(SettingsScreenUIEvent.OnTransactionForListItemClick)
                    }

                    else -> {
                        // No-op
                    }
                }
            },
        ),
        SettingsScreenListItemData(
            data = SettingsListItemDividerData(),
        ),
        SettingsScreenListItemData(
            data = SettingsListItemHeaderData(
                textStringResourceId = R.string.screen_settings_backup_and_restore,
            ),
        ),
        SettingsScreenListItemData(
            data = SettingsListItemContentData(
                isEnabled = !uiState.isLoading,
                imageVector = MyIcons.Backup,
                textStringResourceId = R.string.screen_settings_backup,
            ),
            handleEvent = { event ->
                when (event) {
                    is SettingsListItemContentEvent.OnClick -> {
                        handleUIEvent(SettingsScreenUIEvent.OnBackupDataListItemClick)
                    }

                    else -> {
                        // No-op
                    }
                }
            },
        ),
        SettingsScreenListItemData(
            data = SettingsListItemContentData(
                isEnabled = !uiState.isLoading,
                imageVector = MyIcons.Restore,
                textStringResourceId = R.string.screen_settings_restore,
            ),
            handleEvent = { event ->
                when (event) {
                    is SettingsListItemContentEvent.OnClick -> {
                        handleUIEvent(SettingsScreenUIEvent.OnRestoreDataListItemClick)
                    }

                    else -> {
                        // No-op
                    }
                }
            },
        ),
        SettingsScreenListItemData(
            data = SettingsListItemContentData(
                isEnabled = !uiState.isLoading,
                imageVector = MyIcons.Calculate,
                textStringResourceId = R.string.screen_settings_recalculate_total,
            ),
            handleEvent = { event ->
                when (event) {
                    is SettingsListItemContentEvent.OnClick -> {
                        handleUIEvent(SettingsScreenUIEvent.OnRecalculateTotalListItemClick)
                    }

                    else -> {
                        // No-op
                    }
                }
            },
        ),
        SettingsScreenListItemData(
            data = SettingsListItemDividerData(),
        ),
        SettingsScreenListItemData(
            data = SettingsListItemHeaderData(
                textStringResourceId = R.string.screen_settings_notifications,
            ),
        ),
        SettingsScreenListItemData(
            data = SettingsListItemContentData(
                isChecked = uiState.isReminderEnabled.orFalse(),
                isEnabled = !uiState.isLoading,
                hasToggle = true,
                imageVector = MyIcons.Notifications,
                textStringResourceId = R.string.screen_settings_reminder,
            ),
            handleEvent = { event ->
                when (event) {
                    is SettingsListItemContentEvent.OnCheckedChange -> {
                        handleUIEvent(SettingsScreenUIEvent.OnToggleReminder)
                    }

                    is SettingsListItemContentEvent.OnClick -> {
                        handleUIEvent(SettingsScreenUIEvent.OnToggleReminder)
                    }
                }
            },
        ),
        SettingsScreenListItemData(
            data = SettingsListItemDividerData(),
        ),
        SettingsScreenListItemData(
            data = SettingsListItemHeaderData(
                textStringResourceId = R.string.screen_settings_about,
            ),
        ),
        SettingsScreenListItemData(
            data = SettingsListItemContentData(
                isEnabled = !uiState.isLoading,
                imageVector = MyIcons.TextSnippet,
                textStringResourceId = R.string.screen_settings_credits,
            ),
            handleEvent = { event ->
                when (event) {
                    is SettingsListItemContentEvent.OnClick -> {
                        Toast.makeText(context, "Not Yet Implemented", Toast.LENGTH_SHORT).show()
                    }

                    else -> {
                        // No-op
                    }
                }
            },
        ),
        SettingsScreenListItemData(
            data = SettingsListItemContentData(
                isEnabled = !uiState.isLoading,
                imageVector = MyIcons.TextSnippet,
                textStringResourceId = R.string.screen_settings_open_source_licenses,
            ),
            handleEvent = { event ->
                when (event) {
                    is SettingsListItemContentEvent.OnClick -> {
                        handleUIEvent(SettingsScreenUIEvent.OnOpenSourceLicensesListItemClick)
                    }

                    else -> {
                        // No-op
                    }
                }
            },
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
}

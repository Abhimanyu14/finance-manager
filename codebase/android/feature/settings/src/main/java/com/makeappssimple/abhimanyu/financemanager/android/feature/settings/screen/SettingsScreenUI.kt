package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBalance
import androidx.compose.material.icons.rounded.Backup
import androidx.compose.material.icons.rounded.Calculate
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.Groups
import androidx.compose.material.icons.rounded.Restore
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyLinearProgressIndicator
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.component.listitem.SettingsListItem
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.component.listitem.SettingsListItemData
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.component.listitem.SettingsListItemEvents

enum class SettingsBottomSheetType : BottomSheetType {
    NONE,
}

@Immutable
data class SettingsScreenUIData(
    val isLoading: Boolean = false,
    val appVersion: String? = null,
)

@Immutable
data class SettingsScreenListItemData(
    val data: SettingsListItemData,
    val events: SettingsListItemEvents = SettingsListItemEvents(),
)

@Immutable
internal data class SettingsScreenUIEvents(
    val backupData: () -> Unit = {},
    val navigateToCategoriesScreen: () -> Unit = {},
    val navigateToSourcesScreen: () -> Unit = {},
    val navigateToTransactionForValuesScreen: () -> Unit = {},
    val navigateUp: () -> Unit = {},
    val recalculateTotal: () -> Unit = {},
    val restoreData: () -> Unit = {},
)

@Composable
internal fun SettingsScreenUI(
    events: SettingsScreenUIEvents,
    uiState: SettingsScreenUIState,
    state: CommonScreenUIState = rememberCommonScreenUIState(),
) {
    val listItemsData: List<SettingsScreenListItemData> = listOf(
        SettingsScreenListItemData(
            data = SettingsListItemData(
                isHeading = true,
                isLoading = uiState.isLoading,
                textStringResourceId = R.string.screen_settings_data,
            ),
        ),
        SettingsScreenListItemData(
            data = SettingsListItemData(
                isLoading = uiState.isLoading,
                imageVector = Icons.Rounded.Category,
                textStringResourceId = R.string.screen_settings_categories,
            ),
            events = SettingsListItemEvents(
                onClick = events.navigateToCategoriesScreen,
            ),
        ),
        SettingsScreenListItemData(
            data = SettingsListItemData(
                isLoading = uiState.isLoading,
                imageVector = Icons.Rounded.AccountBalance,
                textStringResourceId = R.string.screen_settings_sources,
            ),
            events = SettingsListItemEvents(
                onClick = events.navigateToSourcesScreen,
            ),
        ),
        SettingsScreenListItemData(
            data = SettingsListItemData(
                isLoading = uiState.isLoading,
                imageVector = Icons.Rounded.Groups,
                textStringResourceId = R.string.screen_settings_transaction_for,
            ),
            events = SettingsListItemEvents(
                onClick = events.navigateToTransactionForValuesScreen,
            ),
        ),
        SettingsScreenListItemData(
            data = SettingsListItemData(
                isHeading = true,
                isLoading = uiState.isLoading,
                textStringResourceId = R.string.screen_settings_backup_and_restore,
            ),
        ),
        SettingsScreenListItemData(
            data = SettingsListItemData(
                isLoading = uiState.isLoading,
                imageVector = Icons.Rounded.Backup,
                textStringResourceId = R.string.screen_settings_backup,
            ),
            events = SettingsListItemEvents(
                onClick = events.backupData,
            ),
        ),
        SettingsScreenListItemData(
            data = SettingsListItemData(
                isLoading = uiState.isLoading,
                imageVector = Icons.Rounded.Restore,
                textStringResourceId = R.string.screen_settings_restore,
            ),
            events = SettingsListItemEvents(
                onClick = events.restoreData,
            ),
        ),
        SettingsScreenListItemData(
            data = SettingsListItemData(
                isLoading = uiState.isLoading,
                imageVector = Icons.Rounded.Calculate,
                textStringResourceId = R.string.screen_settings_recalculate_total,
            ),
            events = SettingsListItemEvents(
                onClick = events.recalculateTotal,
            ),
        ),
    )

    MyScaffold(
        modifier = Modifier
            .fillMaxSize(),
        sheetContent = {
            when (uiState.settingsBottomSheetType) {
                SettingsBottomSheetType.NONE -> {
                    VerticalSpacer()
                }
            }
        },
        sheetState = state.modalBottomSheetState,
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.screen_settings_appbar_title,
                navigationAction = events.navigateUp,
            )
        },
        onClick = {
            state.focusManager.clearFocus()
        },
        coroutineScope = state.coroutineScope,
        onBackPress = uiState.resetBottomSheetType,
    ) {
        // TODO(Abhi) - Change to LazyColumn using sticky footer
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize(),
        ) {
            LazyColumn(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .verticalScroll(
                        state = rememberScrollState(),
                    )
                    .fillMaxWidth()
                    .weight(
                        weight = 1F,
                    ),
            ) {
                item {
                    AnimatedVisibility(
                        visible = uiState.isLoading,
                    ) {
                        MyLinearProgressIndicator(
                            modifier = Modifier
                                .testTag(
                                    tag = "linear_progress_indicator",
                                ),
                        )
                    }
                }
                items(
                    items = listItemsData,
                    key = { listItem ->
                        listItem.hashCode()
                    },
                ) {
                    SettingsListItem(
                        data = it.data,
                        events = it.events,
                    )
                }
            }
            uiState.appVersion?.let {
                MyText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp,
                            vertical = 8.dp,
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
    }
}

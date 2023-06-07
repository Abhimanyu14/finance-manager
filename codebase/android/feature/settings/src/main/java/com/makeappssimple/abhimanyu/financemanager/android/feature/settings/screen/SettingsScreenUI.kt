package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ListItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Backup
import androidx.compose.material.icons.rounded.Calculate
import androidx.compose.material.icons.rounded.Groups
import androidx.compose.material.icons.rounded.Restore
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyLinearProgressIndicator
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.extensions.conditionalClickable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.BottomSheetHandler
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.R

private enum class SettingsBottomSheetType : BottomSheetType {
    NONE,
}

@Immutable
data class SettingsScreenUIData(
    val isLoading: Boolean = false,
    val appVersion: String? = null,
)

@Immutable
internal data class SettingsScreenUIEvents(
    val backupData: () -> Unit,
    val navigateToTransactionForValuesScreen: () -> Unit,
    val navigateUp: () -> Unit,
    val recalculateTotal: () -> Unit,
    val restoreData: () -> Unit,
)

@Composable
internal fun SettingsScreenUI(
    data: SettingsScreenUIData,
    events: SettingsScreenUIEvents,
    state: CommonScreenUIState,
) {
    var settingsBottomSheetType by remember {
        mutableStateOf(
            value = SettingsBottomSheetType.NONE,
        )
    }
    val resetBottomSheetType = {
        settingsBottomSheetType = SettingsBottomSheetType.NONE
    }

    BottomSheetHandler(
        showModalBottomSheet = settingsBottomSheetType != SettingsBottomSheetType.NONE,
        bottomSheetType = settingsBottomSheetType,
        coroutineScope = state.coroutineScope,
        keyboardController = state.keyboardController,
        modalBottomSheetState = state.modalBottomSheetState,
        resetBottomSheetType = resetBottomSheetType,
    )

    MyScaffold(
        sheetState = state.modalBottomSheetState,
        sheetContent = {
            when (settingsBottomSheetType) {
                SettingsBottomSheetType.NONE -> {
                    VerticalSpacer()
                }
            }
        },
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.screen_settings_appbar_title,
                navigationAction = events.navigateUp,
            )
        },
        onClick = {
            state.focusManager.clearFocus()
        },
        backHandlerEnabled = settingsBottomSheetType != SettingsBottomSheetType.NONE,
        coroutineScope = state.coroutineScope,
        onBackPress = resetBottomSheetType,
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Column(
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
                AnimatedVisibility(
                    visible = data.isLoading,
                ) {
                    MyLinearProgressIndicator(
                        modifier = Modifier
                            .testTag(
                                tag = "linear_progress_indicator",
                            ),
                    )
                }
                ListItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Rounded.Backup,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground,
                        )
                    },
                    text = {
                        MyText(
                            modifier = Modifier
                                .fillMaxWidth(),
                            textStringResourceId = R.string.screen_settings_backup,
                            style = MaterialTheme.typography.bodyLarge
                                .copy(
                                    color = MaterialTheme.colorScheme.onBackground,
                                ),
                        )
                    },
                    modifier = Modifier
                        .conditionalClickable(
                            onClick = if (data.isLoading) {
                                null
                            } else {
                                events.backupData
                            },
                        ),
                )
                ListItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Rounded.Restore,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground,
                        )
                    },
                    text = {
                        MyText(
                            modifier = Modifier
                                .fillMaxWidth(),
                            textStringResourceId = R.string.screen_settings_restore,
                            style = MaterialTheme.typography.bodyLarge
                                .copy(
                                    color = MaterialTheme.colorScheme.onBackground,
                                ),
                        )
                    },
                    modifier = Modifier
                        .conditionalClickable(
                            onClick = if (data.isLoading) {
                                null
                            } else {
                                events.restoreData
                            },
                        ),
                )
                ListItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Rounded.Calculate,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground,
                        )
                    },
                    text = {
                        MyText(
                            modifier = Modifier
                                .fillMaxWidth(),
                            textStringResourceId = R.string.screen_settings_recalculate_total,
                            style = MaterialTheme.typography.bodyLarge
                                .copy(
                                    color = MaterialTheme.colorScheme.onBackground,
                                ),
                        )
                    },
                    modifier = Modifier
                        .conditionalClickable(
                            onClick = if (data.isLoading) {
                                null
                            } else {
                                events.recalculateTotal
                            },
                        ),
                )
                ListItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Rounded.Groups,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground,
                        )
                    },
                    text = {
                        MyText(
                            modifier = Modifier
                                .fillMaxWidth(),
                            textStringResourceId = R.string.screen_settings_transaction_for,
                            style = MaterialTheme.typography.bodyLarge
                                .copy(
                                    color = MaterialTheme.colorScheme.onBackground,
                                ),
                        )
                    },
                    modifier = Modifier
                        .conditionalClickable(
                            onClick = if (data.isLoading) {
                                null
                            } else {
                                events.navigateToTransactionForValuesScreen
                            },
                        ),
                )
            }
            data.appVersion?.let {
                MyText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            all = 16.dp,
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

/*
@Preview
@Composable
fun SettingsScreenViewPreview() {
    MyAppTheme {
        SettingsScreenView(
            data = SettingsScreenViewData(
                isLoading = false,
                appVersion = "2023.04.07.1",
                backupData = {},
                navigateToTransactionForValuesScreen = {},
                navigateUp = {},
                recalculateTotal = {},
                restoreData = {},
            ),
            state = rememberCommonScreenViewState(),
        )
    }
}
*/

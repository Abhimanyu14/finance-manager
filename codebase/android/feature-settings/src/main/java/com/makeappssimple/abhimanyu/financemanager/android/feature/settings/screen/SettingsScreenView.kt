package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.screen

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ListItem
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Backup
import androidx.compose.material.icons.rounded.Calculate
import androidx.compose.material.icons.rounded.Groups
import androidx.compose.material.icons.rounded.Restore
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.JSON_MIMETYPE
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyLinearProgressIndicator
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyScaffoldContentWrapper
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.util.navigateToViewTransactionForScreen
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.util.navigateUp
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.BottomSheetBackHandler
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.util.getAppVersion

internal enum class SettingsBottomSheetType : BottomSheetType {
    NONE,
}

@Immutable
internal data class SettingsScreenViewData(
    val isLoading: Boolean,
    val createDocument: ManagedActivityResultLauncher<String, Uri?>,
    val openDocument: ManagedActivityResultLauncher<Array<String>, Uri?>,
    val navigationManager: NavigationManager,
    val recalculateTotal: () -> Unit,
)

@Composable
internal fun SettingsScreenView(
    data: SettingsScreenViewData,
    state: CommonScreenViewState,
) {
    var settingsBottomSheetType by remember {
        mutableStateOf(
            value = SettingsBottomSheetType.NONE,
        )
    }
    val appVersionName = getAppVersion(
        context = state.context,
    )?.versionName

    if (state.modalBottomSheetState.currentValue != ModalBottomSheetValue.Hidden) {
        DisposableEffect(Unit) {
            onDispose {
                settingsBottomSheetType = SettingsBottomSheetType.NONE
                state.keyboardController?.hide()
            }
        }
    }

    BottomSheetBackHandler(
        enabled = settingsBottomSheetType != SettingsBottomSheetType.NONE,
        coroutineScope = state.coroutineScope,
        modalBottomSheetState = state.modalBottomSheetState,
    ) {
        settingsBottomSheetType = SettingsBottomSheetType.NONE
    }

    ModalBottomSheetLayout(
        sheetState = state.modalBottomSheetState,
        sheetContent = {
            when (settingsBottomSheetType) {
                SettingsBottomSheetType.NONE -> {
                    VerticalSpacer()
                }
            }
        },
    ) {
        Scaffold(
            topBar = {
                MyTopAppBar(
                    titleTextStringResourceId = R.string.screen_settings_appbar_title,
                    navigationAction = {
                        navigateUp(
                            navigationManager = data.navigationManager,
                        )
                    },
                )
            },
            modifier = Modifier
                .fillMaxSize(),
        ) { innerPadding ->
            MyScaffoldContentWrapper(
                innerPadding = innerPadding,
                onClick = {
                    state.focusManager.clearFocus()
                },
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
                            MyLinearProgressIndicator()
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
                                .clickable(
                                    enabled = !data.isLoading,
                                    onClick = {
                                        data.createDocument.launch(JSON_MIMETYPE)
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
                                .clickable(
                                    enabled = !data.isLoading,
                                    onClick = {
                                        data.openDocument.launch(arrayOf(JSON_MIMETYPE))
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
                                .clickable(
                                    enabled = !data.isLoading,
                                    onClick = {
                                        data.recalculateTotal()
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
                                .clickable(
                                    enabled = !data.isLoading,
                                    onClick = {
                                        navigateToViewTransactionForScreen(
                                            navigationManager = data.navigationManager,
                                        )
                                    },
                                ),
                        )
                    }
                    appVersionName?.let {
                        MyText(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    all = 16.dp,
                                ),
                            text = "Version - $it",
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
    }
}

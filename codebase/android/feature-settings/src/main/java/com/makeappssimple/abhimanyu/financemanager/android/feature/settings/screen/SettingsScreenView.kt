package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.screen

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ListItem
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Backup
import androidx.compose.material.icons.rounded.Calculate
import androidx.compose.material.icons.rounded.Restore
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyLinearProgressIndicator
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.ScaffoldContentWrapper
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.DarkGray
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.utils.navigateUp
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.BottomSheetBackHandler
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.util.JSON_MIMETYPE

internal enum class SettingsBottomSheetType : BottomSheetType {
    NONE,
}

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
            ScaffoldContentWrapper(
                innerPadding = innerPadding,
                onClick = {
                    state.focusManager.clearFocus()
                },
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxWidth(),
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
                                tint = DarkGray,
                            )
                        },
                        text = {
                            MyText(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                textStringResourceId = R.string.screen_settings_backup,
                                style = TextStyle(
                                    color = DarkGray,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Normal,
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
                                tint = DarkGray,
                            )
                        },
                        text = {
                            MyText(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                textStringResourceId = R.string.screen_settings_restore,
                                style = TextStyle(
                                    color = DarkGray,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Normal,
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
                                tint = DarkGray,
                            )
                        },
                        text = {
                            MyText(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                textStringResourceId = R.string.screen_settings_recalculate_total,
                                style = TextStyle(
                                    color = DarkGray,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Normal,
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
                }
            }
        }
    }
}

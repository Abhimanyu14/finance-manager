package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.settings

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Backup
import androidx.compose.material.icons.rounded.Calculate
import androidx.compose.material.icons.rounded.Restore
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.makeappssimple.abhimanyu.financemanager.android.R
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.ScaffoldContentWrapper
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.MyText
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.DarkGray
import com.makeappssimple.abhimanyu.financemanager.android.utils.CreateJsonDocument
import com.makeappssimple.abhimanyu.financemanager.android.utils.JSON_MIMETYPE

data class SettingsScreenViewData(
    val screenViewModel: SettingsScreenViewModel,
)

@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@Composable
fun SettingsScreenView(
    data: SettingsScreenViewData,
    state: SettingsScreenViewState,
) {
    val createDocument = rememberLauncherForActivityResult(
        contract = CreateJsonDocument(),
    ) { uri ->
        uri?.let {
            data.screenViewModel.backupDataToDocument(
                uri = it,
            )
        }
    }
    val openDocument = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
    ) { uri ->
        uri?.let {
            data.screenViewModel.restoreDataFromDocument(
                uri = it,
            )
        }
    }

    Scaffold(
        topBar = {
            MyTopAppBar(
                navigationManager = data.screenViewModel.navigationManager,
                titleText = stringResource(
                    id = R.string.screen_settings_appbar_title,
                ),
                isNavigationIconVisible = true,
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
                            text = stringResource(
                                id = R.string.screen_settings_backup,
                            ),
                            style = TextStyle(
                                color = DarkGray,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                            ),
                            modifier = Modifier
                                .fillMaxWidth(),
                        )
                    },
                    modifier = Modifier
                        .clickable {
                            createDocument.launch(JSON_MIMETYPE)
                        },
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
                            text = stringResource(
                                id = R.string.screen_settings_restore,
                            ),
                            style = TextStyle(
                                color = DarkGray,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                            ),
                            modifier = Modifier
                                .fillMaxWidth(),
                        )
                    },
                    modifier = Modifier
                        .clickable {
                            openDocument.launch(arrayOf(JSON_MIMETYPE))
                        },
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
                            text = stringResource(
                                id = R.string.screen_settings_recalculate_total,
                            ),
                            style = TextStyle(
                                color = DarkGray,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                            ),
                            modifier = Modifier
                                .fillMaxWidth(),
                        )
                    },
                    modifier = Modifier
                        .clickable {
                            data.screenViewModel.recalculateTotal()
                        },
                )
            }
        }
    }
}

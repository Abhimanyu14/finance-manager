package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.settings

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.R
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.ScaffoldContentWrapper
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.MyText

data class SettingsScreenViewData(
    val screenViewModel: SettingsScreenViewModel,
)

private const val JSON_MIMETYPE = "application/json"

@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@Composable
fun SettingsScreenView(
    data: SettingsScreenViewData,
    state: SettingsScreenViewState,
) {
    val createDocument = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument(
            mimeType = JSON_MIMETYPE,
        ),
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
                    text = {
                        MyText(
                            text = stringResource(
                                id = R.string.screen_settings_backup,
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
                    text = {
                        MyText(
                            text = stringResource(
                                id = R.string.screen_settings_restore,
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
                    text = {
                        MyText(
                            text = stringResource(
                                id = R.string.screen_settings_recalculate_total,
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

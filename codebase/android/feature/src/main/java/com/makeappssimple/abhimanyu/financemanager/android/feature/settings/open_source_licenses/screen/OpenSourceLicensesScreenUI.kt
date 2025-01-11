package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_CONTENT_OPEN_SOURCE_LICENSES
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_OPEN_SOURCE_LICENSES
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.state.CommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.state.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.top_app_bar.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.feature.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.bottomsheet.OpenSourceLicensesScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.event.OpenSourceLicensesScreenUIEvent
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.state.OpenSourceLicensesScreenUIState
import com.mikepenz.aboutlibraries.ui.compose.LibrariesContainer
import com.mikepenz.aboutlibraries.ui.compose.LibraryDefaults

@Composable
internal fun OpenSourceLicensesScreenUI(
    uiState: OpenSourceLicensesScreenUIState,
    state: CommonScreenUIState = rememberCommonScreenUIState(),
    handleUIEvent: (uiEvent: OpenSourceLicensesScreenUIEvent) -> Unit = {},
) {
    MyScaffold(
        modifier = Modifier
            .testTag(
                tag = SCREEN_OPEN_SOURCE_LICENSES,
            )
            .fillMaxSize(),
        sheetContent = {
            when (uiState.screenBottomSheetType) {
                is OpenSourceLicensesScreenBottomSheetType.None -> {
                    VerticalSpacer()
                }
            }
        },
        sheetState = state.modalBottomSheetState,
        snackbarHostState = state.snackbarHostState,
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.screen_open_source_licenses_appbar_title,
                navigationAction = {
                    handleUIEvent(OpenSourceLicensesScreenUIEvent.OnTopAppBarNavigationButtonClick)
                },
            )
        },
        onClick = state.focusManager::clearFocus,
        coroutineScope = state.coroutineScope,
        onBottomSheetDismiss = {
            handleUIEvent(OpenSourceLicensesScreenUIEvent.OnNavigationBackButtonClick)
        },
    ) {
        LibrariesContainer(
            modifier = Modifier
                .testTag(
                    tag = SCREEN_CONTENT_OPEN_SOURCE_LICENSES,
                )
                .fillMaxSize(),
            showAuthor = true,
            showVersion = true,
            showLicenseBadges = true,
            colors = LibraryDefaults
                .libraryColors(
                    backgroundColor = MaterialTheme.colorScheme.background,
                ),
        )
    }
}

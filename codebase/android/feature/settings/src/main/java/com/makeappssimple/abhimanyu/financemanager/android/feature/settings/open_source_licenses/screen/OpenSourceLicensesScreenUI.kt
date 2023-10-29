package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_CONTENT_OPEN_SOURCE_LICENSES
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_OPEN_SOURCE_LICENSES
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.R
import com.mikepenz.aboutlibraries.ui.compose.LibrariesContainer
import com.mikepenz.aboutlibraries.ui.compose.LibraryDefaults

enum class OpenSourceLicensesBottomSheetType : BottomSheetType {
    NONE,
}

@Immutable
data class OpenSourceLicensesScreenUIData(
    val isLoading: Boolean = false,
)

@Immutable
sealed class OpenSourceLicensesScreenUIEvent {
    object NavigateUp : OpenSourceLicensesScreenUIEvent()
}

@Composable
internal fun OpenSourceLicensesScreenUI(
    uiState: OpenSourceLicensesScreenUIState,
    state: CommonScreenUIState = rememberCommonScreenUIState(),
    handleUIEvents: (uiEvent: OpenSourceLicensesScreenUIEvent) -> Unit = {},
) {
    MyScaffold(
        modifier = Modifier
            .testTag(SCREEN_OPEN_SOURCE_LICENSES)
            .fillMaxSize(),
        sheetContent = {
            when (uiState.openSourceLicensesBottomSheetType) {
                OpenSourceLicensesBottomSheetType.NONE -> {
                    VerticalSpacer()
                }
            }
        },
        sheetState = state.modalBottomSheetState,
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.screen_open_source_licenses_appbar_title,
                navigationAction = {
                    handleUIEvents(OpenSourceLicensesScreenUIEvent.NavigateUp)
                },
            )
        },
        onClick = {
            state.focusManager.clearFocus()
        },
        coroutineScope = state.coroutineScope,
        onBackPress = uiState.resetBottomSheetType,
    ) {
        LibrariesContainer(
            modifier = Modifier
                .testTag(SCREEN_CONTENT_OPEN_SOURCE_LICENSES)
                .fillMaxSize(),
            showAuthor = true,
            showVersion = true,
            showLicenseBadges = true,
            colors = LibraryDefaults.libraryColors(
                backgroundColor = MaterialTheme.colorScheme.background,
            ),
        )
    }
}

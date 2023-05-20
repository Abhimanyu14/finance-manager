package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.R

internal enum class AnalysisBottomSheetType : BottomSheetType {
    NONE,
}

@Immutable
internal data class AnalysisScreenViewData(
    val isLoading: Boolean,
)

@Immutable
internal data class AnalysisScreenViewEvents(
    val navigateUp: () -> Unit,
)

@Composable
internal fun AnalysisScreenView(
    data: AnalysisScreenViewData,
    events: AnalysisScreenViewEvents,
    state: CommonScreenViewState,
) {
    var settingsBottomSheetType by remember {
        mutableStateOf(
            value = AnalysisBottomSheetType.NONE,
        )
    }

    if (state.modalBottomSheetState.currentValue != ModalBottomSheetValue.Hidden) {
        DisposableEffect(
            key1 = Unit,
        ) {
            onDispose {
                settingsBottomSheetType = AnalysisBottomSheetType.NONE
                state.keyboardController?.hide()
            }
        }
    }

    MyScaffold(
        sheetState = state.modalBottomSheetState,
        sheetContent = {
            when (settingsBottomSheetType) {
                AnalysisBottomSheetType.NONE -> {
                    VerticalSpacer()
                }
            }
        },
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.screen_analysis_appbar_title,
                navigationAction = events.navigateUp,
            )
        },
        onClick = {
            state.focusManager.clearFocus()
        },
        backHandlerEnabled = settingsBottomSheetType != AnalysisBottomSheetType.NONE,
        coroutineScope = state.coroutineScope,
        onBackPress = {
            settingsBottomSheetType = AnalysisBottomSheetType.NONE
        },
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize(),
        ) {

        }
    }
}

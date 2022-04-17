package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.source_details

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.R
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.ScaffoldContentWrapper
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.toggleModalBottomSheetState
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.sources.SourceListItemView
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.BottomSheetShape

enum class SourceDetailsBottomSheetType {
    NONE,
}

data class SourceDetailsScreenViewData(
    val screenViewModel: SourceDetailsViewModel,
)

@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalMaterialApi
@Composable
fun SourceDetailsScreenView(
    data: SourceDetailsScreenViewData,
    state: SourceDetailsScreenViewState,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val source by data.screenViewModel.source.collectAsState(
        initial = null,
    )
    var sourcesBottomSheetType by remember {
        mutableStateOf(
            value = SourceDetailsBottomSheetType.NONE,
        )
    }

    if (state.modalBottomSheetState.currentValue != ModalBottomSheetValue.Hidden) {
        DisposableEffect(Unit) {
            onDispose {
                sourcesBottomSheetType = SourceDetailsBottomSheetType.NONE
                keyboardController?.hide()
            }
        }
    }
    BackHandler(
        enabled = sourcesBottomSheetType != SourceDetailsBottomSheetType.NONE,
    ) {
        toggleModalBottomSheetState(
            coroutineScope = state.coroutineScope,
            modalBottomSheetState = state.modalBottomSheetState,
        ) {
            sourcesBottomSheetType = SourceDetailsBottomSheetType.NONE
        }
    }

    ModalBottomSheetLayout(
        sheetState = state.modalBottomSheetState,
        sheetShape = BottomSheetShape,
        sheetContent = {
            when (sourcesBottomSheetType) {
                SourceDetailsBottomSheetType.NONE -> {
                    VerticalSpacer()
                }
            }
        },
    ) {
        Scaffold(
            scaffoldState = state.scaffoldState,
            topBar = {
                MyTopAppBar(
                    navigationManager = data.screenViewModel.navigationManager,
                    titleText = stringResource(
                        id = R.string.screen_source_details_appbar_title,
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
                source?.let { source ->
                    SourceListItemView(
                        source = source,
                    )
                }
            }
        }
    }
}

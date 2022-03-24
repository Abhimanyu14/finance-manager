package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.sources

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager

class SourcesScreenViewState constructor(
    val focusManager: FocusManager,
    val scaffoldState: ScaffoldState,
)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun rememberSourcesScreenViewState(
    focusManager: FocusManager = LocalFocusManager.current,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
) = remember {
    SourcesScreenViewState(
        focusManager = focusManager,
        scaffoldState = scaffoldState,
    )
}

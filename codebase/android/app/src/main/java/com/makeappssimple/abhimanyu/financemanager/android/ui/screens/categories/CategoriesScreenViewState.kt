package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.categories

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager

class CategoriesScreenViewState constructor(
    val focusManager: FocusManager,
    val scaffoldState: ScaffoldState,
)

@Composable
fun rememberCategoriesScreenViewState(
    focusManager: FocusManager = LocalFocusManager.current,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
) = remember {
    CategoriesScreenViewState(
        focusManager = focusManager,
        scaffoldState = scaffoldState,
    )
}

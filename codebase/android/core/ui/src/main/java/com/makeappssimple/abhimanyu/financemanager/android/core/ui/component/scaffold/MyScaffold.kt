package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.DrawerDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetDefaults
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.contentColorFor
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyScaffoldContentWrapper
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.BottomSheetExpandedShape
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.BottomSheetShape
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.BottomSheetBackHandler
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyScaffold(
    modifier: Modifier = Modifier,

    // ModalBottomSheetLayout
    sheetContent: @Composable ColumnScope.() -> Unit,
    sheetState: ModalBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden),
    sheetShape: Shape = BottomSheetShape,
    sheetElevation: Dp = ModalBottomSheetDefaults.Elevation,
    sheetBackgroundColor: Color = MaterialTheme.colors.surface,
    sheetContentColor: Color = contentColorFor(sheetBackgroundColor),
    scrimColor: Color = ModalBottomSheetDefaults.scrimColor,

    // Scaffold
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable (SnackbarHostState) -> Unit = { SnackbarHost(it) },
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    isFloatingActionButtonDocked: Boolean = false,
    drawerContent: @Composable (ColumnScope.() -> Unit)? = null,
    drawerGesturesEnabled: Boolean = true,
    drawerShape: Shape = MaterialTheme.shapes.large,
    drawerElevation: Dp = DrawerDefaults.Elevation,
    drawerBackgroundColor: Color = MaterialTheme.colors.surface,
    drawerContentColor: Color = contentColorFor(drawerBackgroundColor),
    drawerScrimColor: Color = DrawerDefaults.scrimColor,
    backgroundColor: Color = MaterialTheme.colors.background,
    contentColor: Color = contentColorFor(backgroundColor),

    // MyScaffoldContentWrapper
    onClick: () -> Unit,

    // BottomSheetBackHandler
    backHandlerEnabled: Boolean,
    coroutineScope: CoroutineScope,
    onBackPress: () -> Unit,

    content: @Composable () -> Unit,
) {
    BottomSheetBackHandler(
        enabled = backHandlerEnabled,
        coroutineScope = coroutineScope,
        modalBottomSheetState = sheetState,
        resetBottomSheetType = onBackPress,
    )

    ModalBottomSheetLayout(
        sheetContent = {
            val screenHeight = LocalConfiguration.current.screenHeightDp.dp
            val bottomSheetModifier = if (sheetShape == BottomSheetExpandedShape) {
                Modifier
                    .fillMaxSize()
            } else {
                Modifier
                    .heightIn(
                        max = screenHeight - 60.dp,
                    )
            }
            Column(
                modifier = bottomSheetModifier,
            ) {
                sheetContent()
            }
        },
        sheetState = sheetState,
        sheetShape = sheetShape,
        sheetElevation = sheetElevation,
        sheetBackgroundColor = sheetBackgroundColor,
        sheetContentColor = sheetContentColor,
        scrimColor = scrimColor,
    ) {
        Scaffold(
            modifier = modifier,
            scaffoldState = scaffoldState,
            topBar = topBar,
            bottomBar = bottomBar,
            snackbarHost = snackbarHost,
            floatingActionButton = floatingActionButton,
            floatingActionButtonPosition = floatingActionButtonPosition,
            isFloatingActionButtonDocked = isFloatingActionButtonDocked,
            drawerContent = drawerContent,
            drawerGesturesEnabled = drawerGesturesEnabled,
            drawerShape = drawerShape,
            drawerElevation = drawerElevation,
            drawerBackgroundColor = drawerBackgroundColor,
            drawerContentColor = drawerContentColor,
            drawerScrimColor = drawerScrimColor,
            backgroundColor = backgroundColor,
            contentColor = contentColor,
        ) { innerPadding ->
            MyScaffoldContentWrapper(
                innerPadding = innerPadding,
                onClick = onClick,
            ) {
                content()
            }
        }
    }
}

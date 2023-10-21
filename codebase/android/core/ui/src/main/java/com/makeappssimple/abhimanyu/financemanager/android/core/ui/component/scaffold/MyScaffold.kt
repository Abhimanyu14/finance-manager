package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyScaffoldContentWrapper
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.BottomSheetExpandedShape
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.BottomSheetShape
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.BottomSheetBackHandler
import kotlinx.coroutines.CoroutineScope

private val topAppBarHeight = 64.dp

@Composable
fun MyScaffold(
    modifier: Modifier = Modifier,

    // ModalBottomSheetLayout
    sheetContent: @Composable ColumnScope.() -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    ),
    sheetShape: Shape = BottomSheetShape,
    sheetBackgroundColor: Color = MaterialTheme.colorScheme.surface,
    scrimColor: Color = BottomSheetDefaults.ScrimColor,

    // Scaffold
    snackbarHostState: SnackbarHostState = SnackbarHostState(),
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {
        SnackbarHost(snackbarHostState)
    },
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = contentColorFor(backgroundColor),

    // MyScaffoldContentWrapper
    onClick: () -> Unit,

    // BottomSheetBackHandler
    isModalBottomSheetVisible: Boolean = false,
    backHandlerEnabled: Boolean = false,
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

    Scaffold(
        modifier = modifier,
        topBar = topBar,
        bottomBar = bottomBar,
        snackbarHost = snackbarHost,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
        containerColor = backgroundColor,
        contentColor = contentColor,
        contentWindowInsets = ScaffoldDefaults
            .contentWindowInsets
            .exclude(WindowInsets.navigationBars)
            .exclude(WindowInsets.ime),
    ) { innerPadding ->
        MyScaffoldContentWrapper(
            innerPadding = innerPadding,
            onClick = onClick,
        ) {
            content()
        }
    }
    if (isModalBottomSheetVisible) {
        ModalBottomSheet(
            sheetState = sheetState,
            shape = sheetShape,
            containerColor = sheetBackgroundColor,
            tonalElevation = 0.dp,
            dragHandle = {},
            scrimColor = scrimColor,
            onDismissRequest = onBackPress,
            windowInsets = WindowInsets(0),
        ) {
            val screenHeight = LocalConfiguration.current.screenHeightDp.dp
            val navigationBarsHeight =
                WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

            val bottomSheetModifier = if (sheetShape == BottomSheetExpandedShape) {
                Modifier
                    .fillMaxSize()
            } else {
                Modifier
                    .heightIn(
                        max = screenHeight + navigationBarsHeight - topAppBarHeight,
                    )
            }

            Column(
                modifier = bottomSheetModifier,
            ) {
                Box(
                    modifier = bottomSheetModifier
                        .animateContentSize(),
                ) {
                    this@Column.sheetContent()
                }
            }
        }
    }
}

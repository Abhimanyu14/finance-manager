package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.systemBars
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyScaffoldContentWrapper
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.BottomSheetExpandedShape
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.BottomSheetShape
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.typealiases.ColumnScopedComposableContent
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.typealiases.ComposableContent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.BottomSheetBackHandler
import kotlinx.coroutines.CoroutineScope

private val topAppBarHeight = 64.dp

@Composable
public fun MyScaffold(
    modifier: Modifier = Modifier,

    // ModalBottomSheetLayout
    sheetContent: ColumnScopedComposableContent = {},
    sheetState: SheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    ),
    sheetShape: Shape = BottomSheetShape,
    sheetBackgroundColor: Color = MaterialTheme.colorScheme.surface,
    scrimColor: Color = BottomSheetDefaults.ScrimColor,

    // Scaffold
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    topBar: ComposableContent = {},
    bottomBar: ComposableContent = {},
    snackbarHost: ComposableContent = {
        SnackbarHost(snackbarHostState)
    },
    floatingActionButton: ComposableContent = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = contentColorFor(backgroundColor),

    // MyScaffoldContentWrapper
    onClick: () -> Unit = {},

    contentTestTag: String = "",

    // BottomSheetBackHandler
    isModalBottomSheetVisible: Boolean = false,
    isBackHandlerEnabled: Boolean = false,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    onNavigationBackButtonClick: () -> Unit = {},

    content: ColumnScopedComposableContent,
) {
    BottomSheetBackHandler(
        isEnabled = isBackHandlerEnabled,
        coroutineScope = coroutineScope,
        modalBottomSheetState = sheetState,
        onBackPressed = onNavigationBackButtonClick,
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
        Box(
            modifier = Modifier
                .testTag(
                    tag = contentTestTag,
                ),
        ) {
            MyScaffoldContentWrapper(
                innerPadding = innerPadding,
                onClick = onClick,
                content = content,
            )
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
            onDismissRequest = onNavigationBackButtonClick,
            windowInsets = WindowInsets.systemBars.only(WindowInsetsSides.Bottom),
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

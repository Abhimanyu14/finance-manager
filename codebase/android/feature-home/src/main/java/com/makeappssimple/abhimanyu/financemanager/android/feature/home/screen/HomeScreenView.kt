package com.makeappssimple.abhimanyu.financemanager.android.feature.home.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.ScaffoldContentWrapper
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.BottomSheetShape
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.utils.navigateToAddTransactionScreen
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.utils.navigateToSourcesScreen
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.utils.navigateToTransactionsScreen
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.BottomSheetBackHandler
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.buttons.MyFloatingActionButton
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.overview_card.OverviewCard
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.total_balance_card.TotalBalanceCard
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.components.HomeBottomAppBar
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.components.HomeListItem
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.components.HomeListItemViewData
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.components.HomeRecentTransactionsView
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.components.bottomsheet.HomeMenuBottomSheetContent

internal enum class HomeBottomSheetType : BottomSheetType {
    NONE,
    MENU,
}

internal data class HomeScreenViewData(
    val homeListItemViewData: List<HomeListItemViewData>,
    val navigationManager: NavigationManager,
)

@Composable
internal fun HomeScreenView(
    data: HomeScreenViewData,
    state: CommonScreenViewState,
) {
    var homeBottomSheetType by remember {
        mutableStateOf(
            value = HomeBottomSheetType.NONE,
        )
    }

    if (state.modalBottomSheetState.currentValue != ModalBottomSheetValue.Hidden) {
        DisposableEffect(Unit) {
            onDispose {
                homeBottomSheetType = HomeBottomSheetType.NONE
                state.keyboardController?.hide()
            }
        }
    }

    BottomSheetBackHandler(
        enabled = homeBottomSheetType != HomeBottomSheetType.NONE,
        coroutineScope = state.coroutineScope,
        modalBottomSheetState = state.modalBottomSheetState,
    ) {
        homeBottomSheetType = HomeBottomSheetType.NONE
    }

    ModalBottomSheetLayout(
        sheetState = state.modalBottomSheetState,
        sheetShape = BottomSheetShape,
        sheetContent = {
            when (homeBottomSheetType) {
                HomeBottomSheetType.NONE -> {
                    VerticalSpacer()
                }
                HomeBottomSheetType.MENU -> {
                    HomeMenuBottomSheetContent(
                        coroutineScope = state.coroutineScope,
                        modalBottomSheetState = state.modalBottomSheetState,
                        navigationManager = data.navigationManager,
                        resetBottomSheetType = {
                            homeBottomSheetType = HomeBottomSheetType.NONE
                        },
                    )
                }
            }
        },
    ) {
        Scaffold(
            topBar = {
                MyTopAppBar(
                    navigationManager = data.navigationManager,
                    titleTextStringResourceId = R.string.screen_home_appbar_title,
                    isNavigationIconVisible = false,
                )
            },
            bottomBar = {
                HomeBottomAppBar(
                    coroutineScope = state.coroutineScope,
                    modalBottomSheetState = state.modalBottomSheetState,
                    updateHomeBottomSheetType = {
                        homeBottomSheetType = HomeBottomSheetType.MENU
                    },
                )
            },
            floatingActionButton = {
                MyFloatingActionButton(
                    iconImageVector = Icons.Rounded.Add,
                    contentDescription = stringResource(
                        id = R.string.screen_home_floating_action_button_content_description,
                    ),
                    onClick = {
                        navigateToAddTransactionScreen(
                            navigationManager = data.navigationManager,
                        )
                    },
                )
            },
            isFloatingActionButtonDocked = true,
            modifier = Modifier
                .fillMaxSize(),
        ) { innerPadding ->
            ScaffoldContentWrapper(
                innerPadding = innerPadding,
                onClick = {
                    state.focusManager.clearFocus()
                },
            ) {
                LazyColumn {
                    item {
                        TotalBalanceCard(
                            onClick = {
                                navigateToSourcesScreen(
                                    navigationManager = data.navigationManager,
                                )
                            },
                        )
                    }
                    item {
                        OverviewCard()
                    }
                    item {
                        HomeRecentTransactionsView(
                            onClick = {
                                navigateToTransactionsScreen(
                                    navigationManager = data.navigationManager,
                                )
                            },
                        )
                    }
                    items(
                        items = data.homeListItemViewData,
                        key = { listItem ->
                            listItem.hashCode()
                        },
                    ) { listItem ->
                        HomeListItem(
                            data = listItem,
                        )
                    }
                    item {
                        VerticalSpacer(
                            height = 48.dp,
                        )
                    }
                }
            }
        }
    }
}

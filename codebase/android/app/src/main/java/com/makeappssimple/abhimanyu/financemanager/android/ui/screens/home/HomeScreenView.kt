package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.R
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateToAddTransactionScreen
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateToCategoriesScreen
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateToSettingsScreen
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateToSourcesScreen
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateToTransactionsScreen
import com.makeappssimple.abhimanyu.financemanager.android.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.BottomSheetBackHandler
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.ScaffoldContentWrapper
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.toggleModalBottomSheetState
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.MyText
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.buttons.MyFloatingActionButton
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.overview_card.OverviewCard
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.total_balance_card.TotalBalanceCard
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.BottomAppBarBackground
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.BottomAppBarIconTint
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.BottomSheetShape
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.DarkGray
import kotlinx.coroutines.CoroutineScope

enum class HomeBottomSheetType : BottomSheetType {
    NONE,
    MENU,
}

data class HomeScreenViewData(
    val screenViewModel: HomeScreenViewModel,
)

@OptIn(
    ExperimentalComposeUiApi::class,
    ExperimentalMaterialApi::class,
)
@Composable
fun HomeScreenView(
    data: HomeScreenViewData,
    state: HomeScreenViewState,
) {
    val homeListItemViewData by data.screenViewModel.homeListItemViewData.collectAsState(
        initial = emptyList(),
    )
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
                        navigationManager = data.screenViewModel.navigationManager,
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
                    navigationManager = data.screenViewModel.navigationManager,
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
                            navigationManager = data.screenViewModel.navigationManager,
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
                                    navigationManager = data.screenViewModel.navigationManager,
                                )
                            },
                        )
                    }
                    item {
                        OverviewCard()
                    }
                    item {
                        RecentTransactionsView(
                            onClick = {
                                navigateToTransactionsScreen(
                                    navigationManager = data.screenViewModel.navigationManager,
                                )
                            },
                        )
                    }
                    items(homeListItemViewData) { listItem ->
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

@OptIn(
    ExperimentalMaterialApi::class,
)
@Composable
private fun HomeMenuBottomSheetContent(
    coroutineScope: CoroutineScope,
    modalBottomSheetState: ModalBottomSheetState,
    navigationManager: NavigationManager,
    resetBottomSheetType: () -> Unit,
) {
    HomeBottomSheet(
        data = HomeBottomSheetData(
            items = listOf(
                HomeBottomSheetItemData(
                    text = stringResource(
                        id = R.string.screen_home_bottom_sheet_sources,
                    ),
                    onClick = {
                        toggleModalBottomSheetState(
                            coroutineScope = coroutineScope,
                            modalBottomSheetState = modalBottomSheetState,
                        ) {
                            resetBottomSheetType()
                            navigateToSourcesScreen(
                                navigationManager = navigationManager,
                            )
                        }
                    },
                ),
                HomeBottomSheetItemData(
                    text = stringResource(
                        id = R.string.screen_home_bottom_sheet_categories,
                    ),
                    onClick = {
                        toggleModalBottomSheetState(
                            coroutineScope = coroutineScope,
                            modalBottomSheetState = modalBottomSheetState,
                        ) {
                            resetBottomSheetType()
                            navigateToCategoriesScreen(
                                navigationManager = navigationManager,
                            )
                        }
                    },
                ),
                HomeBottomSheetItemData(
                    text = stringResource(
                        id = R.string.screen_home_bottom_sheet_settings,
                    ),
                    onClick = {
                        toggleModalBottomSheetState(
                            coroutineScope = coroutineScope,
                            modalBottomSheetState = modalBottomSheetState,
                        ) {
                            resetBottomSheetType()
                            navigateToSettingsScreen(
                                navigationManager = navigationManager,
                            )
                        }
                    },
                ),
            ),
        ),
    )
}

@OptIn(
    ExperimentalMaterialApi::class,
)
@Composable
private fun HomeBottomAppBar(
    coroutineScope: CoroutineScope,
    modalBottomSheetState: ModalBottomSheetState,
    updateHomeBottomSheetType: () -> Unit,
) {
    BottomAppBar(
        backgroundColor = BottomAppBarBackground,
        cutoutShape = CircleShape,
    ) {
        // Leading icons should typically have a high content alpha
        CompositionLocalProvider(
            LocalContentAlpha provides ContentAlpha.high,
        ) {
            IconButton(
                onClick = {
                    updateHomeBottomSheetType()
                    toggleModalBottomSheetState(
                        coroutineScope = coroutineScope,
                        modalBottomSheetState = modalBottomSheetState,
                    )
                },
            ) {
                Icon(
                    imageVector = Icons.Rounded.Menu,
                    contentDescription = stringResource(
                        id = R.string.screen_home_bottom_app_bar_button_content_description,
                    ),
                    tint = BottomAppBarIconTint,
                )
            }
        }
        Spacer(
            modifier = Modifier
                .weight(
                    weight = 1F,
                ),
        )
    }
}

@Composable
private fun RecentTransactionsView(
    onClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 8.dp,
                end = 8.dp,
                top = 16.dp,
                bottom = 8.dp,
            )
            .clip(
                shape = CircleShape,
            )
            .clickable(
                onClickLabel = stringResource(
                    id = R.string.screen_home_view_all_transactions,
                ),
                onClick = onClick,
            )
            .padding(
                top = 12.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 12.dp,
            ),
    ) {
        MyText(
            textStringResourceId = R.string.screen_home_recent_transactions,
            fontWeight = FontWeight.Bold,
            color = DarkGray,
            modifier = Modifier
                .weight(
                    weight = 1F,
                ),
        )
        Icon(
            imageVector = Icons.Rounded.ChevronRight,
            tint = DarkGray,
            contentDescription = stringResource(
                id = R.string.screen_home_view_all_transactions,
            ),
        )
    }
}

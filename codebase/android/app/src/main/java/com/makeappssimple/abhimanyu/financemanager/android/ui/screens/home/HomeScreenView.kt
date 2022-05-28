package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.R
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateToAddTransactionScreen
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateToCategoriesScreen
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateToSettingsScreen
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateToSourcesScreen
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateToTransactionsScreen
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.ScaffoldContentWrapper
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.toggleModalBottomSheetState
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.MyText
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.buttons.MyFloatingActionButton
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.total_balance_card.TotalBalanceCard
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.BottomAppBarBackground
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.BottomAppBarIconTint
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.BottomSheetShape
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.DarkGray

enum class HomeBottomSheetType {
    NONE,
    MENU,
}

data class HomeScreenViewData(
    val screenViewModel: HomeScreenViewModel,
)

@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@Composable
fun HomeScreenView(
    data: HomeScreenViewData,
    state: HomeScreenViewState,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
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
                keyboardController?.hide()
            }
        }
    }
    BackHandler(
        enabled = homeBottomSheetType != HomeBottomSheetType.NONE,
    ) {
        toggleModalBottomSheetState(
            coroutineScope = state.coroutineScope,
            modalBottomSheetState = state.modalBottomSheetState,
        ) {
            homeBottomSheetType = HomeBottomSheetType.NONE
        }
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
                    HomeBottomSheet(
                        data = HomeBottomSheetData(
                            items = listOf(
                                HomeBottomSheetItemData(
                                    text = stringResource(
                                        id = R.string.screen_home_bottom_sheet_sources,
                                    ),
                                    onClick = {
                                        toggleModalBottomSheetState(
                                            coroutineScope = state.coroutineScope,
                                            modalBottomSheetState = state.modalBottomSheetState,
                                        ) {
                                            homeBottomSheetType = HomeBottomSheetType.NONE
                                            navigateToSourcesScreen(
                                                navigationManager = data.screenViewModel.navigationManager,
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
                                            coroutineScope = state.coroutineScope,
                                            modalBottomSheetState = state.modalBottomSheetState,
                                        ) {
                                            homeBottomSheetType = HomeBottomSheetType.NONE
                                            navigateToCategoriesScreen(
                                                navigationManager = data.screenViewModel.navigationManager,
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
                                            coroutineScope = state.coroutineScope,
                                            modalBottomSheetState = state.modalBottomSheetState,
                                        ) {
                                            homeBottomSheetType = HomeBottomSheetType.NONE
                                            navigateToSettingsScreen(
                                                navigationManager = data.screenViewModel.navigationManager,
                                            )
                                        }
                                    },
                                ),
                            ),
                        ),
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
                                homeBottomSheetType = HomeBottomSheetType.MENU
                                toggleModalBottomSheetState(
                                    coroutineScope = state.coroutineScope,
                                    modalBottomSheetState = state.modalBottomSheetState,
                                ) {}
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
                        RecentTransactionsView {
                            navigateToTransactionsScreen(
                                navigationManager = data.screenViewModel.navigationManager,
                            )
                        }
                    }
                    homeListItemViewData.forEach { listItem ->
                        item {
                            HomeListItem(
                                data = listItem,
                            )
                        }
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
                top = 8.dp,
                start = 12.dp,
                end = 12.dp,
                bottom = 8.dp,
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
            tint = Color.DarkGray,
            contentDescription = stringResource(
                id = R.string.screen_home_view_all_transactions,
            ),
        )
    }
}

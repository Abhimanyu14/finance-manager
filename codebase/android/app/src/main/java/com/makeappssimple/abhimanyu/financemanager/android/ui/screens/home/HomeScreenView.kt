package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.R
import com.makeappssimple.abhimanyu.financemanager.android.entities.amount.Amount
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateToAddTransactionScreen
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateToCategoriesScreen
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateToSettingsScreen
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateToSourcesScreen
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MyFloatingActionButton
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.ScaffoldContentWrapper
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.TotalBalanceCard
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.toggleModalBottomSheetState
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.BottomAppBarBackground
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.BottomAppBarIconTint

data class HomeScreenViewData(
    val screenViewModel: HomeViewModel,
)

@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@Composable
fun HomeScreenView(
    data: HomeScreenViewData,
    state: HomeScreenViewState,
) {
    val homeListItemViewData by data.screenViewModel.homeListItemViewData.collectAsState(
        initial = emptyList(),
    )
    val totalBalanceAmount by data.screenViewModel.sourcesTotalBalanceAmountValue.collectAsState(
        initial = 0L,
    )

    ModalBottomSheetLayout(
        sheetState = state.modalBottomSheetState,
        sheetContent = {
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
                                    navigateToSettingsScreen(
                                        navigationManager = data.screenViewModel.navigationManager,
                                    )
                                }
                            },
                        ),
                    ),
                ),
            )
        },
    ) {
        Scaffold(
            scaffoldState = state.scaffoldState,
            topBar = {
                MyTopAppBar(
                    navigationManager = data.screenViewModel.navigationManager,
                    titleText = stringResource(
                        id = R.string.screen_home_appbar_title,
                    ),
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
            floatingActionButtonPosition = FabPosition.End,
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
                            total = Amount(
                                value = totalBalanceAmount,
                            ).toString(),
                            onClick = {
                                navigateToSourcesScreen(
                                    navigationManager = data.screenViewModel.navigationManager,
                                )
                            },
                        )
                    }
                    itemsIndexed(
                        items = homeListItemViewData,
                        key = { _, listItem ->
                            listItem.hashCode()
                        },
                    ) { _, listItem ->
                        HomeListItem(
                            data = listItem,
                            deleteTransaction = {
                                data.screenViewModel.deleteTransaction(
                                    id = listItem.transaction.id,
                                )
                            },
                        )
                    }
                    item {
                        Spacer(
                            modifier = Modifier
                                .height(
                                    height = 48.dp,
                                ),
                        )
                    }
                }
            }
        }
    }
}

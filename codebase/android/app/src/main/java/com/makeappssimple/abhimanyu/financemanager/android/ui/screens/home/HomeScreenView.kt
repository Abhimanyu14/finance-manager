package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.ListItem
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.makeappssimple.abhimanyu.financemanager.android.R
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateToAddTransactionScreen
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateToCategoriesScreen
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateToSourcesScreen
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.toggleModalBottomSheetState
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.BottomAppBarBackground
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.BottomAppBarIconTint
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.FloatingActionButtonBackground
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.FloatingActionButtonIconTint
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Primary
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Surface

data class HomeScreenViewData(
    val screenViewModel: HomeViewModel,
)

@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@Composable
fun HomeScreenView(
    data: HomeScreenViewData,
) {
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )

    val transactions by data.screenViewModel.transactions.collectAsState(
        initial = emptyList(),
    )

    ModalBottomSheetLayout(
        sheetState = modalBottomSheetState,
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
                                    coroutineScope = coroutineScope,
                                    modalBottomSheetState = modalBottomSheetState,
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
                                    coroutineScope = coroutineScope,
                                    modalBottomSheetState = modalBottomSheetState,
                                ) {
                                    navigateToCategoriesScreen(
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
            scaffoldState = scaffoldState,
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = stringResource(
                                id = R.string.screen_home_appbar_title,
                            ),
                            color = Primary,
                        )
                    },
                    modifier = Modifier
                        .background(
                            color = Surface,
                        ),
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
                                    coroutineScope = coroutineScope,
                                    modalBottomSheetState = modalBottomSheetState,
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
                                weight = 1f,
                            ),
                    )
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    backgroundColor = FloatingActionButtonBackground,
                    onClick = {
                        navigateToAddTransactionScreen(
                            navigationManager = data.screenViewModel.navigationManager,
                        )
                    },
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = stringResource(
                            id = R.string.screen_home_floating_action_button_content_description,
                        ),
                        tint = FloatingActionButtonIconTint,
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.End,
            isFloatingActionButtonDocked = true,
            modifier = Modifier
                .fillMaxSize(),
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .background(
                        color = Surface,
                    )
                    .fillMaxSize()
                    .padding(
                        paddingValues = innerPadding,
                    ),
            ) {
                LazyColumn {
                    items(
                        items = transactions,
                    ) { transaction ->
                        ListItem(
                            text = {
                                Text(
                                    text = transaction.amount.toString(),
                                )
                            },
                            secondaryText = {
                                Text(
                                    text = transaction.title,
                                )
                            },
                        )
                    }
                }
            }
        }
    }
}

@ExperimentalMaterial3Api
@Preview
@Composable
private fun HomeScreenViewPreview() {
    /*
    MyAppTheme {
        HomeScreenView(
            data = HomeScreenViewData(
                screenViewModel = hiltViewModel(),
            ),
        )
    }
    */
}

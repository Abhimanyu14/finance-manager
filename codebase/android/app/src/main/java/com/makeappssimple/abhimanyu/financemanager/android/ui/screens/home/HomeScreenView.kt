package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.ContentAlpha
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.makeappssimple.abhimanyu.financemanager.android.R
import com.makeappssimple.abhimanyu.financemanager.android.ui.navigation.utils.navigateToAddTransactionScreen
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.BottomAppBarBackground
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.BottomAppBarIconTint
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.FloatingActionButtonBackground
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.FloatingActionButtonIconTint
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Primary
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Surface

data class HomeScreenViewData(
    val screenViewModel: HomeViewModel,
)

@ExperimentalMaterial3Api
@Composable
fun HomeScreenView(
    data: HomeScreenViewData,
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row {
                        Text(
                            text = stringResource(
                                id = R.string.screen_home_appbar_title,
                            ),
                            color = Primary,
                        )
                    }
                },
                modifier = Modifier
                    .background(Surface),
            )
        },
        bottomBar = {
            Column {
                BottomAppBar(
                    backgroundColor = BottomAppBarBackground,
                    cutoutShape = CircleShape,
                ) {
                    /*
                    TODO-Abhi: Add menu later

                    // Leading icons should typically have a high content alpha
                    CompositionLocalProvider(
                        LocalContentAlpha provides ContentAlpha.high,
                    ) {
                        IconButton(
                            onClick = {
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
                    */
                    Spacer(
                        modifier = Modifier
                            .weight(
                                weight = 1f,
                            ),
                    )
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(BottomAppBarBackground),
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
                .background(Surface)
                .fillMaxSize()
                .padding(
                    paddingValues = innerPadding,
                ),
        ) {
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

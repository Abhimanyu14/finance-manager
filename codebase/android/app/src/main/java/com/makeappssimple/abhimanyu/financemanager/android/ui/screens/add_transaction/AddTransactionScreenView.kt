package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.makeappssimple.abhimanyu.financemanager.android.R
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateUp
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.FloatingActionButtonBackground
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.FloatingActionButtonIconTint
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Primary
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Surface
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.TopBarNavigationIconTint

data class AddTransactionScreenViewData(
    val screenViewModel: AddTransactionViewModel,
)

@ExperimentalMaterial3Api
@Composable
fun AddTransactionScreenView(
    data: AddTransactionScreenViewData,
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
                                id = R.string.screen_add_transaction_appbar_title,
                            ),
                            color = Primary,
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navigateUp(
                                navigationManager = data.screenViewModel.navigationManager,
                            )
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = stringResource(
                                id = R.string.navigation_icon_content_description,
                            ),
                            tint = TopBarNavigationIconTint,
                        )
                    }
                },
                modifier = Modifier
                    .background(Surface),
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                backgroundColor = FloatingActionButtonBackground,
                onClick = {
                },
                modifier = Modifier,
            ) {
                Icon(
                    imageVector = Icons.Rounded.Done,
                    contentDescription = stringResource(
                        id = R.string.screen_add_transaction_floating_action_button_content_description,
                    ),
                    tint = FloatingActionButtonIconTint,
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
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
private fun AddTransactionScreenViewPreview() {
    /*
    MyAppTheme {
        AddTransactionScreenView()
    }
    */
}

package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.IconButton
import androidx.compose.material.ListItem
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.R
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateToAddCategoryScreen
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateUp
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.NavigationArrowBackIcon
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.FloatingActionButtonBackground
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.FloatingActionButtonIconTint
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Primary
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Surface

data class CategoriesScreenViewData(
    val screenViewModel: CategoriesViewModel,
)

@ExperimentalMaterialApi
@Composable
fun CategoriesScreenView(
    data: CategoriesScreenViewData,
) {
    val scaffoldState = rememberScaffoldState()

    val categories by data.screenViewModel.categories.collectAsState(
        initial = emptyList(),
    )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row {
                        Text(
                            text = stringResource(
                                id = R.string.screen_categories_appbar_title,
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
                        NavigationArrowBackIcon()
                    }
                },
                modifier = Modifier
                    .background(
                        color = Surface,
                    ),
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                backgroundColor = FloatingActionButtonBackground,
                onClick = {
                    navigateToAddCategoryScreen(
                        navigationManager = data.screenViewModel.navigationManager,
                    )
                },
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = stringResource(
                        id = R.string.screen_sources_floating_action_button_content_description,
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
            Column {
                categories.forEach { category ->
                    ListItem(
                        text = {
                            Text(
                                text = category.title,
                            )
                        },
                        secondaryText = {
                            Text(
                                text = category.description,
                            )
                        },
                    )
                }
            }
        }
    }
}

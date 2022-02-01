package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.sources

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
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
import com.makeappssimple.abhimanyu.financemanager.android.models.sortOrder
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateToAddSourceScreen
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.NavigationBackButton
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.FloatingActionButtonBackground
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.FloatingActionButtonIconTint
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Primary
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Surface

data class SourcesScreenViewData(
    val screenViewModel: SourcesViewModel,
)

@ExperimentalMaterialApi
@Composable
fun SourcesScreenView(
    data: SourcesScreenViewData,
) {
    val scaffoldState = rememberScaffoldState()

    val sources by data.screenViewModel.sources.collectAsState(
        initial = emptyList(),
    )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(
                            id = R.string.screen_sources_appbar_title,
                        ),
                        color = Primary,
                    )
                },
                navigationIcon = {
                    NavigationBackButton(
                        navigationManager = data.screenViewModel.navigationManager,
                    )
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
                    navigateToAddSourceScreen(
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
            LazyColumn {
                itemsIndexed(
                    items = sources
                        .sortedWith(
                            comparator = compareBy {
                                it.type.sortOrder
                            }
                        ),
                    key = { _, listItem ->
                        listItem.hashCode()
                    },
                ) { _, listItem ->
                    SourceListItem(
                        source = listItem,
                        swipeToDeleteEnabled = !listItem.name.contains(
                            other = "Cash",
                            ignoreCase = false,
                        ),
                        deleteSource = {
                            data.screenViewModel.deleteSource(
                                id = listItem.id,
                            )
                        },
                    )
                }
            }
        }
    }
}

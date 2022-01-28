package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.sources

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.DeleteForever
import androidx.compose.material.rememberDismissState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.R
import com.makeappssimple.abhimanyu.financemanager.android.models.sortOrder
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateToAddSourceScreen
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateUp
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.NavigationArrowBackIcon
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
                    Row {
                        Text(
                            text = stringResource(
                                id = R.string.screen_sources_appbar_title,
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
                    val dismissState = rememberDismissState(
                        confirmStateChange = { dismissValue ->
                            when (dismissValue) {
                                DismissValue.DismissedToEnd -> {
                                    data.screenViewModel.deleteSource(
                                        id = listItem.id,
                                    )
                                    true
                                }
                                DismissValue.DismissedToStart -> {
                                    false
                                }
                                DismissValue.Default -> {
                                    false
                                }
                            }
                        },
                    )

                    SwipeToDismiss(
                        state = dismissState,
                        directions = mutableSetOf(
                            DismissDirection.StartToEnd,
                        ),
                        background = {

                            val color by animateColorAsState(
                                when (dismissState.targetValue) {
                                    DismissValue.Default -> LightGray
                                    DismissValue.DismissedToEnd -> Red
                                    DismissValue.DismissedToStart -> White
                                }
                            )
                            val scale by animateFloatAsState(
                                if (dismissState.targetValue == DismissValue.Default) {
                                    1f
                                } else {
                                    1.25f
                                }
                            )

                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        color = color,
                                    ),
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.DeleteForever,
                                    contentDescription = null,
                                    tint = White,
                                    modifier = Modifier
                                        .weight(
                                            weight = 1f,
                                        )
                                        .scale(
                                            scale = scale,
                                        )
                                        .padding(
                                            start = 16.dp,
                                        ),
                                )
                            }
                        },
                    ) {
                        SourceListItem(
                            source = listItem,
                        )
                    }
                }
            }
        }
    }
}

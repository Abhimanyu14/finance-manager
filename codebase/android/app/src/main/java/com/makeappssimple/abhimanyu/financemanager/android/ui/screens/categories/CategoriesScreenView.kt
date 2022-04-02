package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.categories

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DeleteForever
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.makeappssimple.abhimanyu.financemanager.android.R
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateToAddCategoryScreen
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MyFloatingActionButton
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.ScaffoldContentWrapper
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.getDismissState
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Primary
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Surface

data class CategoriesScreenViewData(
    val screenViewModel: CategoriesViewModel,
)

@OptIn(ExperimentalPagerApi::class)
@ExperimentalMaterialApi
@Composable
fun CategoriesScreenView(
    data: CategoriesScreenViewData,
    state: CategoriesScreenViewState,
) {
    val categories by data.screenViewModel.categories.collectAsState(
        initial = emptyList(),
    )
    var selectedTabIndex by remember {
        mutableStateOf(
            value = 0,
        )
    }
    val transactionTypes = listOf(
        TransactionType.EXPENSE,
        TransactionType.INCOME,
    )

    Scaffold(
        scaffoldState = state.scaffoldState,
        topBar = {
            MyTopAppBar(
                navigationManager = data.screenViewModel.navigationManager,
                titleText = stringResource(
                    id = R.string.screen_categories_appbar_title,
                ),
                isNavigationIconVisible = true,
            )
        },
        floatingActionButton = {
            MyFloatingActionButton(
                contentDescription = stringResource(
                    id = R.string.screen_sources_floating_action_button_content_description,
                ),
                onClick = {
                    navigateToAddCategoryScreen(
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
            Column {
                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    backgroundColor = Surface,
                    contentColor = Primary,
                ) {
                    transactionTypes
                        .map {
                            it.title
                        }
                        .forEachIndexed { index, title ->
                            Tab(
                                text = {
                                    Text(
                                        text = title,
                                        color = if (selectedTabIndex == index) {
                                            Primary
                                        } else {
                                            Color.DarkGray
                                        },
                                        fontWeight = FontWeight.Bold,
                                    )
                                },
                                selected = selectedTabIndex == index,
                                onClick = {
                                    selectedTabIndex = index
                                },
                                selectedContentColor = Primary,
                                unselectedContentColor = Primary,
                            )
                        }
                }
                LazyColumn {
                    itemsIndexed(
                        items = categories
                            .filter {
                                it.transactionType == transactionTypes[selectedTabIndex]
                            },
                        key = { _, listItem ->
                            listItem.hashCode()
                        },
                    ) { _, listItem ->
                        val dismissState = getDismissState(
                            dismissedToEndAction = {
                                data.screenViewModel.deleteCategory(
                                    id = listItem.id,
                                )
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
                                        DismissValue.Default -> Color.LightGray
                                        DismissValue.DismissedToEnd -> Color.Red
                                        DismissValue.DismissedToStart -> Color.White
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
                                        tint = Color.White,
                                        modifier = Modifier
                                            .weight(
                                                weight = 1F,
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
                            CategoryListItem(
                                category = listItem,
                            )
                        }
                    }
                }
            }
        }
    }
}

package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.categories

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.R
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateToAddCategoryScreen
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateToEditCategoryScreen
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MyFloatingActionButton
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.ScaffoldContentWrapper
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Primary
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Surface

data class CategoriesScreenViewData(
    val screenViewModel: CategoriesViewModel,
)

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
    var expandedItemIndex by remember {
        mutableStateOf(
            value = -1,
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
                iconImageVector = Icons.Rounded.Add,
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
                                            DarkGray
                                        },
                                        fontWeight = FontWeight.Bold,
                                    )
                                },
                                selected = selectedTabIndex == index,
                                onClick = {
                                    selectedTabIndex = index
                                    expandedItemIndex = -1
                                },
                                selectedContentColor = Primary,
                                unselectedContentColor = Primary,
                            )
                        }
                }
                VerticalSpacer(
                    height = 16.dp,
                )
                LazyColumn {
                    itemsIndexed(
                        items = categories
                            .filter {
                                it.transactionType == transactionTypes[selectedTabIndex]
                            },
                        key = { _, listItem ->
                            listItem.hashCode()
                        },
                    ) { index, listItem ->
                        CategoryListItem(
                            category = listItem,
                            expanded = index == expandedItemIndex,
                            onClick = {
                                expandedItemIndex = if (index == expandedItemIndex) {
                                    -1
                                } else {
                                    index
                                }
                            },
                            onEditClick = {
                                navigateToEditCategoryScreen(
                                    navigationManager = data.screenViewModel.navigationManager,
                                    categoryId = listItem.id,
                                )
                                expandedItemIndex = -1
                            },
                            onDeleteClick = {
                                data.screenViewModel.deleteCategory(
                                    id = listItem.id,
                                )
                                expandedItemIndex = -1
                            },
                        )
                    }
                    item {
                        VerticalSpacer(
                            height = 80.dp,
                        )
                    }
                }
            }
        }
    }
}

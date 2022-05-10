package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.categories

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
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
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.toggleModalBottomSheetState
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.sources.SourcesSetAsDefaultBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.sources.SourcesSetAsDefaultBottomSheetData
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.BottomSheetShape
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Primary
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Surface
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.utils.isDefaultCategory
import com.makeappssimple.abhimanyu.financemanager.android.utils.isSalaryCategory

enum class CategoriesBottomSheetType {
    NONE,
    SET_AS_DEFAULT_CONFIRMATION,
}

data class CategoriesScreenViewData(
    val screenViewModel: CategoriesScreenViewModel,
)

@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalMaterialApi
@Composable
fun CategoriesScreenView(
    data: CategoriesScreenViewData,
    state: CategoriesScreenViewState,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val selectedTabIndex by data.screenViewModel.selectedTabIndex.collectAsState()
    val categories by data.screenViewModel.filteredCategories.collectAsState(
        initial = emptyList(),
    )
    val categoriesIsUsedInTransactions by data.screenViewModel.categoriesIsUsedInTransactions.collectAsState(
        initial = emptyList(),
    )
    val defaultExpenseCategoryId by data.screenViewModel.defaultExpenseCategoryId.collectAsState(
        initial = null,
    )
    val defaultIncomeCategoryId by data.screenViewModel.defaultIncomeCategoryId.collectAsState(
        initial = null,
    )
    var categoriesBottomSheetType by remember {
        mutableStateOf(
            value = CategoriesBottomSheetType.NONE,
        )
    }
    var expandedItemIndex by remember {
        mutableStateOf(
            value = -1,
        )
    }
    var clickedItemId by remember {
        mutableStateOf(
            value = -1,
        )
    }
    val transactionTypes = listOf(
        TransactionType.EXPENSE,
        TransactionType.INCOME,
    )

    if (state.modalBottomSheetState.currentValue != ModalBottomSheetValue.Hidden) {
        DisposableEffect(Unit) {
            onDispose {
                categoriesBottomSheetType = CategoriesBottomSheetType.NONE
                keyboardController?.hide()
            }
        }
    }
    BackHandler(
        enabled = categoriesBottomSheetType != CategoriesBottomSheetType.NONE,
    ) {
        toggleModalBottomSheetState(
            coroutineScope = state.coroutineScope,
            modalBottomSheetState = state.modalBottomSheetState,
        ) {
            categoriesBottomSheetType = CategoriesBottomSheetType.NONE
        }
    }

    ModalBottomSheetLayout(
        sheetState = state.modalBottomSheetState,
        sheetShape = BottomSheetShape,
        sheetContent = {
            when (categoriesBottomSheetType) {
                CategoriesBottomSheetType.NONE -> {
                    VerticalSpacer()
                }
                CategoriesBottomSheetType.SET_AS_DEFAULT_CONFIRMATION -> {
                    val transactionType: TransactionType = transactionTypes[selectedTabIndex]
                    SourcesSetAsDefaultBottomSheet(
                        data = SourcesSetAsDefaultBottomSheetData(
                            title = stringResource(
                                id = R.string.screen_categories_bottom_sheet_set_as_default_title,
                            ),
                            message = stringResource(
                                id = R.string.screen_categories_bottom_sheet_set_as_default_message,
                                transactionType.title.lowercase(),
                            ),
                            positiveButtonText = stringResource(
                                id = R.string.screen_categories_bottom_sheet_set_as_default_positive_button_text,
                            ),
                            negativeButtonText = stringResource(
                                id = R.string.screen_categories_bottom_sheet_set_as_default_negative_button_text,
                            ),
                            onPositiveButtonClick = {
                                toggleModalBottomSheetState(
                                    coroutineScope = state.coroutineScope,
                                    modalBottomSheetState = state.modalBottomSheetState,
                                ) {
                                    data.screenViewModel.setDefaultCategoryIdInDataStore(
                                        defaultCategoryId = clickedItemId,
                                        transactionType = transactionType,
                                    )
                                    categoriesBottomSheetType = CategoriesBottomSheetType.NONE
                                    clickedItemId = -1
                                }
                            },
                            onNegativeButtonClick = {
                                toggleModalBottomSheetState(
                                    coroutineScope = state.coroutineScope,
                                    modalBottomSheetState = state.modalBottomSheetState,
                                ) {
                                    categoriesBottomSheetType = CategoriesBottomSheetType.NONE
                                    clickedItemId = -1
                                }
                            },
                        ),
                    )
                }
            }
        },
    ) {
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
                                        data.screenViewModel.updateSelectedTabIndex(
                                            updatedSelectedTabIndex = index,
                                        )
                                        expandedItemIndex = -1
                                    },
                                    selectedContentColor = Primary,
                                    unselectedContentColor = Primary,
                                )
                            }
                    }
                    LazyColumn {
                        item {
                            VerticalSpacer(
                                height = 16.dp,
                            )
                        }
                        itemsIndexed(
                            items = categories,
                            key = { _, listItem ->
                                listItem.hashCode()
                            },
                        ) { index, listItem ->
                            val deleteEnabled: Boolean? = categoriesIsUsedInTransactions.getOrNull(
                                index = index,
                            )?.not()
                            val transactionType = transactionTypes[selectedTabIndex]
                            val isDefault = if (transactionType == TransactionType.EXPENSE) {
                                if (defaultExpenseCategoryId.isNull()) {
                                    isDefaultCategory(
                                        category = listItem.title,
                                    )
                                } else {
                                    defaultExpenseCategoryId == listItem.id
                                }
                            } else {
                                if (defaultIncomeCategoryId.isNull()) {
                                    isSalaryCategory(
                                        category = listItem.title,
                                    )
                                } else {
                                    defaultIncomeCategoryId == listItem.id
                                }
                            }
                            CategoriesListItem(
                                category = listItem,
                                expanded = index == expandedItemIndex,
                                deleteEnabled = deleteEnabled ?: false,
                                isDefault = isDefault,
                                onClick = {
                                    expandedItemIndex = if (index == expandedItemIndex) {
                                        -1
                                    } else {
                                        index
                                    }
                                },
                                onLongClick = {
                                    if (!isDefault) {
                                        categoriesBottomSheetType =
                                            CategoriesBottomSheetType.SET_AS_DEFAULT_CONFIRMATION
                                        clickedItemId = listItem.id
                                        toggleModalBottomSheetState(
                                            coroutineScope = state.coroutineScope,
                                            modalBottomSheetState = state.modalBottomSheetState,
                                        ) {}
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
}

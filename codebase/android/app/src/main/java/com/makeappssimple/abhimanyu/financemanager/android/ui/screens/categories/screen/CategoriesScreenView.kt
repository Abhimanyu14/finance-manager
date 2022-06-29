package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.categories.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.makeappssimple.abhimanyu.financemanager.android.R
import com.makeappssimple.abhimanyu.financemanager.android.entities.category.Category
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateToAddCategoryScreen
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateToEditCategoryScreen
import com.makeappssimple.abhimanyu.financemanager.android.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.BottomSheetBackHandler
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.ScaffoldContentWrapper
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.toggleModalBottomSheetState
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.buttons.MyFloatingActionButton
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.categories.components.CategoriesDeleteConfirmationBottomSheetContent
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.categories.components.CategoriesListItem
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.categories.components.CategoriesSetAsDefaultConfirmationBottomSheetContent
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.categories.components.CategoriesTabData
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.categories.components.CategoriesTabRow
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.categories.components.CategoriesTabRowData
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.BottomSheetShape
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.utils.isDefaultCategory
import com.makeappssimple.abhimanyu.financemanager.android.utils.isSalaryCategory
import kotlinx.coroutines.launch

enum class CategoriesBottomSheetType : BottomSheetType {
    NONE,
    SET_AS_DEFAULT_CONFIRMATION,
    DELETE_CONFIRMATION,
}

data class CategoriesScreenViewData(
    val defaultExpenseCategoryId: Int?,
    val defaultIncomeCategoryId: Int?,
    val selectedTabIndex: Int,
    val expenseCategoryIsUsedInTransactions: List<Boolean>,
    val incomeCategoryIsUsedInTransactions: List<Boolean>,
    val expenseCategories: List<Category>,
    val incomeCategories: List<Category>,
    val navigationManager: NavigationManager,
    val deleteCategory: (categoryId: Int) -> Unit,
    val setDefaultCategoryIdInDataStore: (
        defaultCategoryId: Int,
        transactionType: TransactionType,
    ) -> Unit,
    val updateSelectedTabIndex: (updatedSelectedTabIndex: Int) -> Unit,
)

@Composable
fun CategoriesScreenView(
    data: CategoriesScreenViewData,
    state: CategoriesScreenViewState,
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
    )
    var categoriesBottomSheetType by remember {
        mutableStateOf(
            value = CategoriesBottomSheetType.NONE,
        )
    }
    var expenseExpandedItemIndex: Int? by remember {
        mutableStateOf(
            value = null,
        )
    }
    var incomeExpandedItemIndex: Int? by remember {
        mutableStateOf(
            value = null,
        )
    }
    var clickedItemId: Int? by remember {
        mutableStateOf(
            value = null,
        )
    }
    var categoryIdToDelete: Int? by remember {
        mutableStateOf(
            value = null,
        )
    }
    val transactionTypes = listOf(
        TransactionType.EXPENSE,
        TransactionType.INCOME,
    )

    LaunchedEffect(
        key1 = pagerState.currentPage,
    ) {
        data.updateSelectedTabIndex(pagerState.currentPage)
    }
    LaunchedEffect(
        key1 = data.selectedTabIndex,
    ) {
        state.coroutineScope.launch {
            pagerState.animateScrollToPage(data.selectedTabIndex)
        }
    }

    if (state.modalBottomSheetState.currentValue != ModalBottomSheetValue.Hidden) {
        DisposableEffect(Unit) {
            onDispose {
                categoriesBottomSheetType = CategoriesBottomSheetType.NONE
                state.keyboardController?.hide()
            }
        }
    }

    BottomSheetBackHandler(
        enabled = categoriesBottomSheetType != CategoriesBottomSheetType.NONE,
        coroutineScope = state.coroutineScope,
        modalBottomSheetState = state.modalBottomSheetState,
    ) {
        categoriesBottomSheetType = CategoriesBottomSheetType.NONE
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
                    CategoriesSetAsDefaultConfirmationBottomSheetContent(
                        coroutineScope = state.coroutineScope,
                        modalBottomSheetState = state.modalBottomSheetState,
                        transactionType = transactionTypes[data.selectedTabIndex],
                        clickedItemId = clickedItemId,
                        resetBottomSheetType = {
                            categoriesBottomSheetType = CategoriesBottomSheetType.NONE
                        },
                        resetClickedItemId = {
                            clickedItemId = null
                        },
                        setDefaultCategoryIdInDataStore = {
                            clickedItemId?.let { clickedItemIdValue ->
                                data.setDefaultCategoryIdInDataStore(
                                    clickedItemIdValue,
                                    transactionTypes[data.selectedTabIndex],
                                )
                            }
                        },
                    )
                }
                CategoriesBottomSheetType.DELETE_CONFIRMATION -> {
                    CategoriesDeleteConfirmationBottomSheetContent(
                        coroutineScope = state.coroutineScope,
                        modalBottomSheetState = state.modalBottomSheetState,
                        categoryIdToDelete = categoryIdToDelete,
                        resetBottomSheetType = {
                            categoriesBottomSheetType = CategoriesBottomSheetType.NONE
                        },
                        resetCategoryIdToDelete = {
                            categoryIdToDelete = null
                        },
                        resetExpenseExpandedItemIndex = {
                            expenseExpandedItemIndex = null
                        },
                        resetIncomeExpandedItemIndex = {
                            incomeExpandedItemIndex = null
                        },
                        deleteCategory = {
                            categoryIdToDelete?.let { categoryIdToDeleteValue ->
                                data.deleteCategory(categoryIdToDeleteValue)
                            }
                        },
                    )
                }
            }
        },
    ) {
        Scaffold(
            topBar = {
                MyTopAppBar(
                    navigationManager = data.navigationManager,
                    titleTextStringResourceId = R.string.screen_categories_appbar_title,
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
                            navigationManager = data.navigationManager,
                        )
                    },
                )
            },
            modifier = Modifier
                .fillMaxSize(),
        ) { innerPadding ->
            ScaffoldContentWrapper(
                innerPadding = innerPadding,
                onClick = {
                    state.focusManager.clearFocus()
                },
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    CategoriesTabRow(
                        data = CategoriesTabRowData(
                            selectedTabIndex = data.selectedTabIndex,
                            updateSelectedTabIndex = {
                                data.updateSelectedTabIndex(it)
                            },
                            tabData = transactionTypes
                                .map {
                                    CategoriesTabData(
                                        title = it.title
                                    )
                                },
                        ),
                    )
                    HorizontalPager(
                        count = 2,
                        state = pagerState,
                        modifier = Modifier
                            .weight(
                                weight = 1F,
                            ),
                    ) { page ->
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize(),
                        ) {
                            val transactionType = transactionTypes[page]
                            item {
                                VerticalSpacer(
                                    height = 16.dp,
                                )
                            }
                            itemsIndexed(
                                items = if (transactionType == TransactionType.EXPENSE) {
                                    data.expenseCategories
                                } else {
                                    data.incomeCategories
                                },
                                key = { _, listItem ->
                                    listItem.hashCode()
                                },
                            ) { index, listItem ->
                                val deleteEnabled: Boolean? =
                                    if (transactionType == TransactionType.EXPENSE) {
                                        data.expenseCategoryIsUsedInTransactions.getOrNull(
                                            index = index,
                                        )?.not()
                                    } else {
                                        data.incomeCategoryIsUsedInTransactions.getOrNull(
                                            index = index,
                                        )?.not()
                                    }
                                val isDefault = if (transactionType == TransactionType.EXPENSE) {
                                    if (data.defaultExpenseCategoryId.isNull()) {
                                        isDefaultCategory(
                                            category = listItem.title,
                                        )
                                    } else {
                                        data.defaultExpenseCategoryId == listItem.id
                                    }
                                } else {
                                    if (data.defaultIncomeCategoryId.isNull()) {
                                        isSalaryCategory(
                                            category = listItem.title,
                                        )
                                    } else {
                                        data.defaultIncomeCategoryId == listItem.id
                                    }
                                }
                                CategoriesListItem(
                                    category = listItem,
                                    expanded = if (transactionType == TransactionType.EXPENSE) {
                                        index == expenseExpandedItemIndex
                                    } else {
                                        index == incomeExpandedItemIndex
                                    },
                                    deleteEnabled = deleteEnabled ?: false,
                                    isDefault = isDefault,
                                    onClick = {
                                        if (transactionType == TransactionType.EXPENSE) {
                                            expenseExpandedItemIndex =
                                                if (index == expenseExpandedItemIndex) {
                                                    null
                                                } else {
                                                    index
                                                }
                                        } else {
                                            incomeExpandedItemIndex =
                                                if (index == incomeExpandedItemIndex) {
                                                    null
                                                } else {
                                                    index
                                                }
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
                                            navigationManager = data.navigationManager,
                                            categoryId = listItem.id,
                                        )
                                        expenseExpandedItemIndex = null
                                        incomeExpandedItemIndex = null
                                    },
                                    onDeleteClick = {
                                        categoryIdToDelete = listItem.id
                                        categoriesBottomSheetType =
                                            CategoriesBottomSheetType.DELETE_CONFIRMATION
                                        toggleModalBottomSheetState(
                                            coroutineScope = state.coroutineScope,
                                            modalBottomSheetState = state.modalBottomSheetState,
                                        )
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
}

package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.screen

import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Immutable
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
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyScaffoldContentWrapper
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyTabData
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyTabRow
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.buttons.MyFloatingActionButton
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.BottomSheetShape
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.util.navigateToAddCategoryScreen
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.util.navigateUp
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.BottomSheetBackHandler
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.toggleModalBottomSheetState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultExpenseCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultIncomeCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultInvestmentCategory
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.components.CategoriesListItem
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.components.bottomsheet.CategoriesDeleteConfirmationBottomSheetContent
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.components.bottomsheet.CategoriesSetAsDefaultConfirmationBottomSheetContent
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.components.bottomsheet.CategoryMenuBottomSheetContent
import kotlinx.coroutines.launch

@Immutable
internal sealed class CategoriesBottomSheetType : BottomSheetType {
    data class CategoryMenu(
        val deleteEnabled: Boolean,
        val isDefault: Boolean,
        val categoryId: Int,
        val categoryTitle: String,
    ) : CategoriesBottomSheetType()

    object DeleteConfirmation : CategoriesBottomSheetType()

    object None : CategoriesBottomSheetType()

    object SetAsDefaultConfirmation : CategoriesBottomSheetType()
}

@Immutable
internal data class CategoriesScreenViewData(
    val defaultExpenseCategoryId: Int?,
    val defaultIncomeCategoryId: Int?,
    val defaultInvestmentCategoryId: Int?,
    val selectedTabIndex: Int,
    val expenseCategoryIsUsedInTransactions: List<Boolean>,
    val incomeCategoryIsUsedInTransactions: List<Boolean>,
    val investmentCategoryIsUsedInTransactions: List<Boolean>,
    val expenseCategories: List<Category>,
    val incomeCategories: List<Category>,
    val investmentCategories: List<Category>,
    val navigationManager: NavigationManager,
    val deleteCategory: (categoryId: Int) -> Unit,
    val setDefaultCategoryIdInDataStore: (
        defaultCategoryId: Int,
        transactionType: TransactionType,
    ) -> Unit,
    val updateSelectedTabIndex: (updatedSelectedTabIndex: Int) -> Unit,
)

@Composable
internal fun CategoriesScreenView(
    data: CategoriesScreenViewData,
    state: CommonScreenViewState,
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
    )
    var categoriesBottomSheetType: CategoriesBottomSheetType by remember {
        mutableStateOf(
            value = CategoriesBottomSheetType.None,
        )
    }
    val resetBottomSheetType = {
        categoriesBottomSheetType = CategoriesBottomSheetType.None
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
        TransactionType.INVESTMENT,
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
                resetBottomSheetType()
                state.keyboardController?.hide()
            }
        }
    }

    BottomSheetBackHandler(
        enabled = categoriesBottomSheetType != CategoriesBottomSheetType.None,
        coroutineScope = state.coroutineScope,
        modalBottomSheetState = state.modalBottomSheetState,
    ) {
        resetBottomSheetType()
    }

    ModalBottomSheetLayout(
        sheetState = state.modalBottomSheetState,
        sheetShape = BottomSheetShape,
        sheetContent = {
            when (categoriesBottomSheetType) {
                is CategoriesBottomSheetType.CategoryMenu -> {
                    val bottomSheetData =
                        categoriesBottomSheetType as CategoriesBottomSheetType.CategoryMenu
                    CategoryMenuBottomSheetContent(
                        deleteEnabled = bottomSheetData.deleteEnabled,
                        isDefault = bottomSheetData.isDefault,
                        coroutineScope = state.coroutineScope,
                        categoryId = bottomSheetData.categoryId,
                        modalBottomSheetState = state.modalBottomSheetState,
                        navigationManager = data.navigationManager,
                        categoryTitle = bottomSheetData.categoryTitle,
                        onDeleteClick = {
                            categoryIdToDelete = bottomSheetData.categoryId
                            categoriesBottomSheetType =
                                CategoriesBottomSheetType.DeleteConfirmation
                        },
                        onSetAsDefaultClick = {
                            clickedItemId = bottomSheetData.categoryId
                            categoriesBottomSheetType =
                                CategoriesBottomSheetType.SetAsDefaultConfirmation
                        },
                        resetBottomSheetType = resetBottomSheetType,
                    )
                }
                is CategoriesBottomSheetType.DeleteConfirmation -> {
                    CategoriesDeleteConfirmationBottomSheetContent(
                        coroutineScope = state.coroutineScope,
                        modalBottomSheetState = state.modalBottomSheetState,
                        categoryIdToDelete = categoryIdToDelete,
                        resetBottomSheetType = resetBottomSheetType,
                        resetCategoryIdToDelete = {
                            categoryIdToDelete = null
                        },
                        deleteCategory = {
                            categoryIdToDelete?.let { categoryIdToDeleteValue ->
                                data.deleteCategory(categoryIdToDeleteValue)
                            }
                        },
                    )
                }
                is CategoriesBottomSheetType.None -> {
                    VerticalSpacer()
                }
                is CategoriesBottomSheetType.SetAsDefaultConfirmation -> {
                    CategoriesSetAsDefaultConfirmationBottomSheetContent(
                        coroutineScope = state.coroutineScope,
                        modalBottomSheetState = state.modalBottomSheetState,
                        transactionType = transactionTypes[data.selectedTabIndex],
                        clickedItemId = clickedItemId,
                        resetBottomSheetType = resetBottomSheetType,
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
            }
        },
    ) {
        Scaffold(
            topBar = {
                MyTopAppBar(
                    titleTextStringResourceId = R.string.screen_categories_appbar_title,
                    navigationAction = {
                        navigateUp(
                            navigationManager = data.navigationManager,
                        )
                    },
                )
            },
            floatingActionButton = {
                MyFloatingActionButton(
                    iconImageVector = Icons.Rounded.Add,
                    contentDescription = stringResource(
                        id = R.string.screen_categories_floating_action_button_content_description,
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
            MyScaffoldContentWrapper(
                innerPadding = innerPadding,
                onClick = {
                    state.focusManager.clearFocus()
                },
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    MyTabRow(
                        selectedTabIndex = data.selectedTabIndex,
                        updateSelectedTabIndex = data.updateSelectedTabIndex,
                        tabData = transactionTypes
                            .map {
                                MyTabData(
                                    title = it.title,
                                )
                            },
                    )
                    HorizontalPager(
                        count = 3,
                        state = pagerState,
                        modifier = Modifier
                            .weight(
                                weight = 1F,
                            ),
                    ) { page ->

                        // To remove overscroll effect
                        CompositionLocalProvider(
                            LocalOverscrollConfiguration provides null
                        ) {
                            LazyVerticalGrid(
                                columns = GridCells.Adaptive(
                                    minSize = 100.dp,
                                ),
                                modifier = Modifier
                                    .fillMaxSize(),
                            ) {
                                val transactionType = transactionTypes[page]
                                itemsIndexed(
                                    items = when (transactionType) {
                                        TransactionType.EXPENSE -> {
                                            data.expenseCategories
                                        }
                                        TransactionType.INCOME -> {
                                            data.incomeCategories
                                        }
                                        else -> {
                                            // Only for investment
                                            data.investmentCategories
                                        }
                                    },
                                    key = { _, listItem ->
                                        listItem.hashCode()
                                    },
                                ) { index, listItem ->
                                    val isDefault = when (transactionType) {
                                        TransactionType.EXPENSE -> {
                                            if (data.defaultExpenseCategoryId.isNull()) {
                                                isDefaultExpenseCategory(
                                                    category = listItem.title,
                                                )
                                            } else {
                                                data.defaultExpenseCategoryId == listItem.id
                                            }
                                        }
                                        TransactionType.INCOME -> {
                                            if (data.defaultIncomeCategoryId.isNull()) {
                                                isDefaultIncomeCategory(
                                                    category = listItem.title,
                                                )
                                            } else {
                                                data.defaultIncomeCategoryId == listItem.id
                                            }
                                        }
                                        else -> {
                                            // Only for investment
                                            if (data.defaultInvestmentCategoryId.isNull()) {
                                                isDefaultInvestmentCategory(
                                                    category = listItem.title,
                                                )
                                            } else {
                                                data.defaultInvestmentCategoryId == listItem.id
                                            }
                                        }
                                    }
                                    val deleteEnabled: Boolean = when (transactionType) {
                                        TransactionType.EXPENSE -> {
                                            !isDefault && data.expenseCategoryIsUsedInTransactions.getOrNull(
                                                index = index,
                                            )?.not() ?: false
                                        }
                                        TransactionType.INCOME -> {
                                            !isDefault && data.incomeCategoryIsUsedInTransactions.getOrNull(
                                                index = index,
                                            )?.not() ?: false
                                        }
                                        else -> {
                                            // Only for investment
                                            !isDefault && data.investmentCategoryIsUsedInTransactions.getOrNull(
                                                index = index,
                                            )?.not() ?: false
                                        }
                                    }

                                    CategoriesListItem(
                                        isDefault = isDefault,
                                        category = listItem,
                                        onClick = {
                                            categoriesBottomSheetType =
                                                CategoriesBottomSheetType.CategoryMenu(
                                                    deleteEnabled = deleteEnabled,
                                                    isDefault = isDefault,
                                                    categoryId = listItem.id,
                                                    categoryTitle = listItem.title,
                                                )
                                            clickedItemId = listItem.id
                                            toggleModalBottomSheetState(
                                                coroutineScope = state.coroutineScope,
                                                modalBottomSheetState = state.modalBottomSheetState,
                                            )
                                        },
                                    )
                                }
                                item(
                                    span = {
                                        GridItemSpan(maxLineSpan)
                                    }
                                ) {
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
}

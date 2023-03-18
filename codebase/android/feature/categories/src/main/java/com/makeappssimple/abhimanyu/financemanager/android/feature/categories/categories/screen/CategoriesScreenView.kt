package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyTabData
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyTabRow
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.buttons.MyFloatingActionButton
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.util.navigateToAddCategoryScreen
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.util.navigateUp
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.toggleModalBottomSheetState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.grid.CategoriesGrid
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.grid_item.CategoriesGridItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultExpenseCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultIncomeCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultInvestmentCategory
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.components.bottomsheet.CategoriesDeleteConfirmationBottomSheetContent
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.components.bottomsheet.CategoriesSetAsDefaultConfirmationBottomSheetContent
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.components.bottomsheet.CategoryMenuBottomSheetContent
import kotlinx.coroutines.launch

@Immutable
internal sealed class CategoriesBottomSheetType : BottomSheetType {
    object DeleteConfirmation : CategoriesBottomSheetType()
    object None : CategoriesBottomSheetType()
    object SetAsDefaultConfirmation : CategoriesBottomSheetType()

    data class Menu(
        val deleteEnabled: Boolean,
        val isDefault: Boolean,
        val categoryId: Int,
        val categoryTitle: String,
    ) : CategoriesBottomSheetType()
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
    val tabData = remember {
        transactionTypes
            .map {
                MyTabData(
                    title = it.title,
                )
            }
    }

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

    MyScaffold(
        sheetState = state.modalBottomSheetState,
        sheetContent = {
            when (categoriesBottomSheetType) {
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

                is CategoriesBottomSheetType.Menu -> {
                    val bottomSheetData =
                        categoriesBottomSheetType as CategoriesBottomSheetType.Menu
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
            }
        },
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
                        transactionType = when (data.selectedTabIndex) {
                            0 -> {
                                TransactionType.EXPENSE.title
                            }

                            1 -> {
                                TransactionType.INCOME.title
                            }

                            else -> {
                                TransactionType.INVESTMENT.title
                            }
                        }
                    )
                },
            )
        },
        onClick = {
            state.focusManager.clearFocus()
        },
        backHandlerEnabled = categoriesBottomSheetType != CategoriesBottomSheetType.None,
        coroutineScope = state.coroutineScope,
        onBackPress = {
            resetBottomSheetType()
        },
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            MyTabRow(
                selectedTabIndex = data.selectedTabIndex,
                updateSelectedTabIndex = data.updateSelectedTabIndex,
                tabData = tabData,
            )
            HorizontalPager(
                count = 3,
                state = pagerState,
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .weight(
                        weight = 1F,
                    ),
            ) { page ->
                val transactionType: TransactionType = transactionTypes[page]
                val categoriesGridItemDataList: List<CategoriesGridItemData> =
                    when (transactionType) {
                        TransactionType.EXPENSE -> {
                            data.expenseCategories.map { category ->
                                CategoriesGridItemData(
                                    isSelected = if (data.defaultExpenseCategoryId.isNull()) {
                                        isDefaultExpenseCategory(
                                            category = category.title,
                                        )
                                    } else {
                                        data.defaultExpenseCategoryId == category.id
                                    },
                                    category = category,
                                )
                            }
                        }

                        TransactionType.INCOME -> {
                            data.incomeCategories.map { category ->
                                CategoriesGridItemData(
                                    isSelected = if (data.defaultIncomeCategoryId.isNull()) {
                                        isDefaultIncomeCategory(
                                            category = category.title,
                                        )
                                    } else {
                                        data.defaultIncomeCategoryId == category.id
                                    },
                                    category = category,
                                )
                            }
                        }

                        TransactionType.INVESTMENT -> {
                            data.investmentCategories.map { category ->
                                CategoriesGridItemData(
                                    isSelected = if (data.defaultInvestmentCategoryId.isNull()) {
                                        isDefaultInvestmentCategory(
                                            category = category.title,
                                        )
                                    } else {
                                        data.defaultInvestmentCategoryId == category.id
                                    },
                                    category = category,
                                )
                            }
                        }

                        else -> {
                            data.investmentCategories.map { category ->
                                CategoriesGridItemData(
                                    isSelected = false,
                                    category = category,
                                )
                            }
                        }
                    }

                CategoriesGrid(
                    bottomPadding = 80.dp,
                    topPadding = 8.dp,
                    categoriesGridItemDataList = categoriesGridItemDataList,
                    onItemClick = { index ->
                        val deleteEnabled = when (transactionType) {
                            TransactionType.EXPENSE -> {
                                !categoriesGridItemDataList[index].isSelected && data.expenseCategoryIsUsedInTransactions.getOrNull(
                                    index = index,
                                )?.not() ?: false
                            }

                            TransactionType.INCOME -> {
                                !categoriesGridItemDataList[index].isSelected && data.incomeCategoryIsUsedInTransactions.getOrNull(
                                    index = index,
                                )?.not() ?: false
                            }

                            TransactionType.INVESTMENT -> {
                                !categoriesGridItemDataList[index].isSelected && data.investmentCategoryIsUsedInTransactions.getOrNull(
                                    index = index,
                                )?.not() ?: false
                            }

                            else -> {
                                false
                            }
                        }

                        categoriesBottomSheetType =
                            CategoriesBottomSheetType.Menu(
                                deleteEnabled = deleteEnabled,
                                isDefault = categoriesGridItemDataList[index].isSelected,
                                categoryId = categoriesGridItemDataList[index].category.id,
                                categoryTitle = categoriesGridItemDataList[index].category.title,
                            )
                        clickedItemId = categoriesGridItemDataList[index].category.id
                        toggleModalBottomSheetState(
                            coroutineScope = state.coroutineScope,
                            modalBottomSheetState = state.modalBottomSheetState,
                        )
                    },
                )
            }
        }
    }
}

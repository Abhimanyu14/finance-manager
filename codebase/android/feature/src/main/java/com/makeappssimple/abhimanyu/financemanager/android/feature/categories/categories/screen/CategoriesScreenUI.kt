package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.constants.TestTags.SCREEN_CATEGORIES
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.constants.TestTags.SCREEN_CONTENT_CATEGORIES
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions.orEmpty
import com.makeappssimple.abhimanyu.financemanager.android.cre.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.cre.designsystem.component.button.MyFloatingActionButton
import com.makeappssimple.abhimanyu.financemanager.android.cre.designsystem.component.navigationBarLandscapeSpacer
import com.makeappssimple.abhimanyu.financemanager.android.cre.designsystem.component.navigationBarsSpacer
import com.makeappssimple.abhimanyu.financemanager.android.cre.designsystem.component.tabrow.MyTabRow
import com.makeappssimple.abhimanyu.financemanager.android.cre.designsystem.icons.MyIcons
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.common.BottomSheetHandler
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.common.state.CommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.common.state.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.bottomsheet.category.CategoriesDeleteConfirmationBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.bottomsheet.category.CategoriesDeleteConfirmationBottomSheetEvent
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.bottomsheet.category.CategoriesSetAsDefaultConfirmationBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.bottomsheet.category.CategoriesSetAsDefaultConfirmationBottomSheetData
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.bottomsheet.category.CategoriesSetAsDefaultConfirmationBottomSheetEvent
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.bottomsheet.category.CategoryMenuBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.bottomsheet.category.CategoryMenuBottomSheetData
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.grid.CategoriesGrid
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.griditem.CategoriesGridItemData
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.top_app_bar.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.feature.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.bottomsheet.CategoriesScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.event.CategoriesScreenUIEvent
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.screen.CategoriesScreenUIConstants.PAGE_COUNT
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.snackbar.CategoriesScreenSnackbarType
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.state.CategoriesScreenUIState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.launch

private object CategoriesScreenUIConstants {
    const val PAGE_COUNT = 3
}

@Composable
internal fun CategoriesScreenUI(
    uiState: CategoriesScreenUIState,
    state: CommonScreenUIState = rememberCommonScreenUIState(),
    handleUIEvent: (uiEvent: CategoriesScreenUIEvent) -> Unit = {},
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState: PagerState = rememberPagerState(
        pageCount = { PAGE_COUNT },
    )

    val setDefaultCategoryFailedSnackbarText = stringResource(
        id = R.string.screen_categories_set_default_category_failed,
    )
    val setDefaultCategorySuccessfulSnackbarText = stringResource(
        id = R.string.screen_categories_set_default_category_successful,
    )

    LaunchedEffect(
        key1 = uiState.screenSnackbarType,
        key2 = handleUIEvent,
    ) {
        when (uiState.screenSnackbarType) {
            CategoriesScreenSnackbarType.None -> {}

            CategoriesScreenSnackbarType.SetDefaultCategoryFailed -> {
                launch {
                    val result = state.snackbarHostState.showSnackbar(
                        message = setDefaultCategoryFailedSnackbarText,
                    )
                    when (result) {
                        SnackbarResult.ActionPerformed -> {}
                        SnackbarResult.Dismissed -> {
                            handleUIEvent(CategoriesScreenUIEvent.OnSnackbarDismissed)
                        }
                    }
                }
            }

            CategoriesScreenSnackbarType.SetDefaultCategorySuccessful -> {
                launch {
                    val result = state.snackbarHostState.showSnackbar(
                        message = setDefaultCategorySuccessfulSnackbarText,
                    )
                    when (result) {
                        SnackbarResult.ActionPerformed -> {}
                        SnackbarResult.Dismissed -> {
                            handleUIEvent(CategoriesScreenUIEvent.OnSnackbarDismissed)
                        }
                    }
                }
            }
        }
    }

    BottomSheetHandler(
        isBottomSheetVisible = uiState.isBottomSheetVisible,
        screenBottomSheetType = uiState.screenBottomSheetType,
        coroutineScope = state.coroutineScope,
        modalBottomSheetState = state.modalBottomSheetState,
        keyboardController = state.keyboardController,
    )

    MyScaffold(
        modifier = Modifier
            .testTag(
                tag = SCREEN_CATEGORIES,
            )
            .fillMaxSize(),
        sheetContent = {
            when (uiState.screenBottomSheetType) {
                is CategoriesScreenBottomSheetType.DeleteConfirmation -> {
                    CategoriesDeleteConfirmationBottomSheet(
                        handleEvent = { event ->
                            when (event) {
                                CategoriesDeleteConfirmationBottomSheetEvent.OnNegativeButtonClick -> {
                                    handleUIEvent(CategoriesScreenUIEvent.OnCategoriesDeleteConfirmationBottomSheet.NegativeButtonClick)
                                }

                                CategoriesDeleteConfirmationBottomSheetEvent.OnPositiveButtonClick -> {
                                    handleUIEvent(CategoriesScreenUIEvent.OnCategoriesDeleteConfirmationBottomSheet.PositiveButtonClick)
                                }
                            }
                        },
                    )
                }

                is CategoriesScreenBottomSheetType.None -> {
                    VerticalSpacer()
                }

                is CategoriesScreenBottomSheetType.SetAsDefaultConfirmation -> {
                    CategoriesSetAsDefaultConfirmationBottomSheet(
                        data = CategoriesSetAsDefaultConfirmationBottomSheetData(
                            transactionType = uiState.validTransactionTypes[pagerState.settledPage],
                        ),
                        handleEvent = { event ->
                            when (event) {
                                CategoriesSetAsDefaultConfirmationBottomSheetEvent.OnNegativeButtonClick -> {
                                    handleUIEvent(CategoriesScreenUIEvent.OnCategoriesSetAsDefaultConfirmationBottomSheet.NegativeButtonClick)
                                }

                                CategoriesSetAsDefaultConfirmationBottomSheetEvent.OnPositiveButtonClick -> {
                                    handleUIEvent(
                                        CategoriesScreenUIEvent.OnCategoriesSetAsDefaultConfirmationBottomSheet.PositiveButtonClick(
                                            selectedTabIndex = pagerState.settledPage,
                                        )
                                    )
                                }
                            }
                        },
                    )
                }

                is CategoriesScreenBottomSheetType.Menu -> {
                    CategoryMenuBottomSheet(
                        data = CategoryMenuBottomSheetData(
                            isDeleteVisible = uiState.screenBottomSheetType.isDeleteVisible,
                            isEditVisible = uiState.screenBottomSheetType.isEditVisible,
                            isSetAsDefaultVisible = uiState.screenBottomSheetType.isSetAsDefaultVisible,
                        ),
                        onDeleteClick = {
                            handleUIEvent(
                                CategoriesScreenUIEvent.OnCategoryMenuBottomSheet.DeleteButtonClick(
                                    categoryId = uiState.screenBottomSheetType.categoryId,
                                )
                            )
                        },
                        onEditClick = {
                            handleUIEvent(
                                CategoriesScreenUIEvent.OnCategoryMenuBottomSheet.EditButtonClick(
                                    categoryId = uiState.screenBottomSheetType.categoryId,
                                )
                            )
                        },
                        onSetAsDefaultClick = {
                            handleUIEvent(
                                CategoriesScreenUIEvent.OnCategoryMenuBottomSheet.SetAsDefaultButtonClick(
                                    categoryId = uiState.screenBottomSheetType.categoryId,
                                )
                            )
                        },
                    )
                }
            }
        },
        sheetState = state.modalBottomSheetState,
        snackbarHostState = state.snackbarHostState,
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.screen_categories_appbar_title,
                navigationAction = {
                    handleUIEvent(CategoriesScreenUIEvent.OnTopAppBarNavigationButtonClick)
                },
            )
        },
        floatingActionButton = {
            MyFloatingActionButton(
                modifier = Modifier
                    .navigationBarsSpacer(),
                iconImageVector = MyIcons.Add,
                contentDescription = stringResource(
                    id = R.string.screen_categories_floating_action_button_content_description,
                ),
                onClick = {
                    handleUIEvent(
                        CategoriesScreenUIEvent.OnFloatingActionButtonClick(
                            transactionType = when (pagerState.settledPage) {
                                0 -> {
                                    TransactionType.EXPENSE.title
                                }

                                1 -> {
                                    TransactionType.INCOME.title
                                }

                                else -> {
                                    TransactionType.INVESTMENT.title
                                }
                            },
                        )
                    )
                },
            )
        },
        onClick = state.focusManager::clearFocus,
        isModalBottomSheetVisible = uiState.isBottomSheetVisible,
        isBackHandlerEnabled = uiState.screenBottomSheetType != CategoriesScreenBottomSheetType.None,
        coroutineScope = state.coroutineScope,
        onBottomSheetDismiss = {
            handleUIEvent(CategoriesScreenUIEvent.OnNavigationBackButtonClick)
        },
    ) {
        Column(
            modifier = Modifier
                .testTag(
                    tag = SCREEN_CONTENT_CATEGORIES,
                )
                .fillMaxSize()
                .navigationBarLandscapeSpacer(),
        ) {
            MyTabRow(
                selectedTabIndex = pagerState.currentPage,
                tabDataList = uiState.tabData,
                updateSelectedTabIndex = { updatedSelectedTabIndex ->
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(updatedSelectedTabIndex)
                    }
                },
            )
            HorizontalPager(
                state = pagerState,
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .weight(
                        weight = 1F,
                    ),
            ) { page ->
                uiState.validTransactionTypes.getOrNull(page)?.let { transactionType ->
                    val categoriesGridItemDataList: ImmutableList<CategoriesGridItemData> =
                        uiState.categoriesGridItemDataMap[transactionType].orEmpty()

                    CategoriesGrid(
                        bottomPadding = 80.dp,
                        topPadding = 8.dp,
                        categoriesGridItemDataList = categoriesGridItemDataList,
                        onItemClick = { index ->

                            // TODO(Abhi): Move this logic outside the UI composable
                            val isDeleteVisible =
                                categoriesGridItemDataList[index].isDeleteVisible ?: false
                            val isEditVisible =
                                categoriesGridItemDataList[index].isEditVisible ?: false
                            val isSetAsDefaultVisible =
                                categoriesGridItemDataList[index].isSetAsDefaultVisible ?: false
                            val categoryId = categoriesGridItemDataList[index].category.id

                            handleUIEvent(
                                CategoriesScreenUIEvent.OnCategoriesGridItemClick(
                                    isDeleteVisible = isDeleteVisible,
                                    isEditVisible = isEditVisible,
                                    isSetAsDefaultVisible = isSetAsDefaultVisible,
                                    categoryId = categoryId,
                                )
                            )
                        },
                    )
                }
            }
        }
    }
}

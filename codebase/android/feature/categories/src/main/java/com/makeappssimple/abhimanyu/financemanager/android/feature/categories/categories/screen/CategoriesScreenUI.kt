package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_CATEGORIES
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_CONTENT_CATEGORIES
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orEmpty
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.button.MyFloatingActionButton
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.navigationBarLandscapeSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.navigationBarsSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.tabrow.MyTabRow
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.icons.MyIcons
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.BottomSheetHandler
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.state.CommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.state.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.category.CategoriesDeleteConfirmationBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.category.CategoriesDeleteConfirmationBottomSheetEvent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.category.CategoriesSetAsDefaultConfirmationBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.category.CategoriesSetAsDefaultConfirmationBottomSheetData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.category.CategoriesSetAsDefaultConfirmationBottomSheetEvent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.category.CategoryMenuBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.category.CategoryMenuBottomSheetData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.grid.CategoriesGrid
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.griditem.CategoriesGridItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.top_app_bar.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.bottomsheet.CategoriesScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.event.CategoriesScreenUIEvent
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.screen.CategoriesScreenUIConstants.PAGE_COUNT
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
    val pagerState: PagerState = rememberPagerState(
        pageCount = { PAGE_COUNT },
    )

    LaunchedEffect(
        key1 = pagerState.currentPage,
        key2 = handleUIEvent,
    ) {
        handleUIEvent(CategoriesScreenUIEvent.OnSelectedTabIndexUpdated(pagerState.currentPage))
    }

    LaunchedEffect(
        key1 = uiState.selectedTabIndex,
    ) {
        state.coroutineScope.launch {
            pagerState.animateScrollToPage(uiState.selectedTabIndex)
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
                            transactionType = uiState.validTransactionTypes[uiState.selectedTabIndex],
                        ),
                        handleEvent = { event ->
                            when (event) {
                                CategoriesSetAsDefaultConfirmationBottomSheetEvent.OnNegativeButtonClick -> {
                                    handleUIEvent(CategoriesScreenUIEvent.OnCategoriesSetAsDefaultConfirmationBottomSheet.NegativeButtonClick)
                                }

                                CategoriesSetAsDefaultConfirmationBottomSheetEvent.OnPositiveButtonClick -> {
                                    handleUIEvent(CategoriesScreenUIEvent.OnCategoriesSetAsDefaultConfirmationBottomSheet.PositiveButtonClick)
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
                            transactionType = when (uiState.selectedTabIndex) {
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
        onNavigationBackButtonClick = {
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
                selectedTabIndex = uiState.selectedTabIndex,
                tabDataList = uiState.tabData,
                updateSelectedTabIndex = { updatedSelectedTabIndex ->
                    handleUIEvent(
                        CategoriesScreenUIEvent.OnSelectedTabIndexUpdated(
                            updatedSelectedTabIndex = updatedSelectedTabIndex,
                        )
                    )
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

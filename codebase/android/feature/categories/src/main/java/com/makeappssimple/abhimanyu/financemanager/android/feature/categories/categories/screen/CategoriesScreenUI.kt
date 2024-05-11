package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_CATEGORIES
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.SCREEN_CONTENT_CATEGORIES
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyTabRow
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.button.MyFloatingActionButton
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.navigationBarLandscapeSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.navigationBarsSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.icons.MyIcons
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.BottomSheetHandler
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.category.CategoriesDeleteConfirmationBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.category.CategoriesSetAsDefaultConfirmationBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.category.CategoryMenuBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.grid.CategoriesGrid
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.griditem.CategoriesGridItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.top_app_bar.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.R
import kotlinx.coroutines.launch

@Composable
internal fun CategoriesScreenUI(
    uiState: CategoriesScreenUIState,
    state: CommonScreenUIState = rememberCommonScreenUIState(),
    handleUIEvent: (uiEvent: CategoriesScreenUIEvent) -> Unit = {},
) {
    LaunchedEffect(
        key1 = uiState.pagerState.currentPage,
    ) {
        handleUIEvent(CategoriesScreenUIEvent.OnSelectedTabIndexUpdated(uiState.pagerState.currentPage))
    }

    LaunchedEffect(
        key1 = uiState.selectedTabIndex,
    ) {
        state.coroutineScope.launch {
            uiState.pagerState.animateScrollToPage(uiState.selectedTabIndex)
        }
    }

    BottomSheetHandler(
        showModalBottomSheet = uiState.screenBottomSheetType != CategoriesScreenBottomSheetType.None,
        screenBottomSheetType = uiState.screenBottomSheetType,
        coroutineScope = state.coroutineScope,
        keyboardController = state.keyboardController,
        modalBottomSheetState = state.modalBottomSheetState,
        resetBottomSheetType = {
            handleUIEvent(CategoriesScreenUIEvent.OnBottomSheetDismissed)
        },
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
                        onNegativeButtonClick = {
                            uiState.setCategoryIdToDelete(null)
                            handleUIEvent(CategoriesScreenUIEvent.OnBottomSheetDismissed)
                        },
                        onPositiveButtonClick = {
                            uiState.categoryIdToDelete?.let { categoryIdToDeleteValue ->
                                handleUIEvent(
                                    CategoriesScreenUIEvent.OnCategoriesDeleteConfirmationBottomSheet.DeleteButtonClick(
                                        categoryId = categoryIdToDeleteValue,
                                    )
                                )
                            }
                            uiState.setCategoryIdToDelete(null)
                            handleUIEvent(CategoriesScreenUIEvent.OnBottomSheetDismissed)
                        },
                    )
                }

                is CategoriesScreenBottomSheetType.None -> {
                    VerticalSpacer()
                }

                is CategoriesScreenBottomSheetType.SetAsDefaultConfirmation -> {
                    CategoriesSetAsDefaultConfirmationBottomSheet(
                        transactionType = uiState.validTransactionTypes[uiState.selectedTabIndex],
                        onNegativeButtonClick = {
                            handleUIEvent(CategoriesScreenUIEvent.OnCategoriesSetAsDefaultConfirmationBottomSheet.NegativeButtonClick)
                        },
                        onPositiveButtonClick = {
                            handleUIEvent(CategoriesScreenUIEvent.OnCategoriesSetAsDefaultConfirmationBottomSheet.PositiveButtonClick)
                        },
                    )
                }

                is CategoriesScreenBottomSheetType.Menu -> {
                    val bottomSheetData = uiState.screenBottomSheetType

                    CategoryMenuBottomSheet(
                        isDeleteVisible = bottomSheetData.isDeleteVisible,
                        isEditVisible = bottomSheetData.isEditVisible,
                        isSetAsDefaultVisible = bottomSheetData.isSetAsDefaultVisible,
                        onDeleteClick = {
                            uiState.setCategoryIdToDelete(bottomSheetData.categoryId)
                            uiState.setScreenBottomSheetType(CategoriesScreenBottomSheetType.DeleteConfirmation)
                        },
                        onEditClick = {
                            uiState.resetScreenBottomSheetType()
                            handleUIEvent(
                                CategoriesScreenUIEvent.OnCategoriesDeleteConfirmationBottomSheet.EditButtonClick(
                                    categoryId = bottomSheetData.categoryId,
                                )
                            )
                        },
                        onSetAsDefaultClick = {
                            uiState.setClickedItemId(bottomSheetData.categoryId)
                            uiState.setScreenBottomSheetType(CategoriesScreenBottomSheetType.SetAsDefaultConfirmation)
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
        isModalBottomSheetVisible = uiState.screenBottomSheetType != CategoriesScreenBottomSheetType.None,
        backHandlerEnabled = uiState.screenBottomSheetType != CategoriesScreenBottomSheetType.None,
        coroutineScope = state.coroutineScope,
        onBackPress = {
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
                state = uiState.pagerState,
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .weight(
                        weight = 1F,
                    ),
            ) { page ->
                val transactionType: TransactionType = uiState.validTransactionTypes[page]
                val categoriesGridItemDataList: List<CategoriesGridItemData> =
                    uiState.categoriesGridItemDataMap[transactionType].orEmpty()

                CategoriesGrid(
                    bottomPadding = 80.dp,
                    topPadding = 8.dp,
                    categoriesGridItemDataList = categoriesGridItemDataList,
                    onItemClick = { index ->

                        // TODO(Abhi): Move this logic outside the UI composable
                        val isDeleteVisible =
                            categoriesGridItemDataList[index].isDeleteVisible ?: false
                        val isEditVisible = categoriesGridItemDataList[index].isEditVisible ?: false
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

package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyTabRow
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.buttons.MyFloatingActionButton
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.navigationBarLandscapeSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.navigationBarsSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.testing.constants.TestTags.SCREEN_CATEGORIES
import com.makeappssimple.abhimanyu.financemanager.android.core.testing.constants.TestTags.SCREEN_CONTENT_CATEGORIES
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.BottomSheetHandler
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.grid.CategoriesGrid
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.grid_item.CategoriesGridItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.component.bottomsheet.CategoriesDeleteConfirmationBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.component.bottomsheet.CategoriesSetAsDefaultConfirmationBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.component.bottomsheet.CategoryMenuBottomSheet
import kotlinx.coroutines.launch

@Immutable
sealed class CategoriesBottomSheetType : BottomSheetType {
    object DeleteConfirmation : CategoriesBottomSheetType()
    object None : CategoriesBottomSheetType()
    object SetAsDefaultConfirmation : CategoriesBottomSheetType()

    data class Menu(
        val isDeleteVisible: Boolean,
        val isEditVisible: Boolean,
        val isSetAsDefaultVisible: Boolean,
        val categoryId: Int,
    ) : CategoriesBottomSheetType()
}

@Immutable
data class CategoriesScreenUIData(
    val selectedTabIndex: Int = 0,
    val categoriesGridItemDataMap: Map<TransactionType, List<CategoriesGridItemData>> = emptyMap(),
)

@Immutable
sealed class CategoriesScreenUIEvent {
    object NavigateUp : CategoriesScreenUIEvent()

    data class DeleteCategory(
        val categoryId: Int,
    ) : CategoriesScreenUIEvent()

    data class NavigateToAddCategoryScreen(
        val transactionType: String,
    ) : CategoriesScreenUIEvent()

    data class NavigateToEditCategoryScreen(
        val categoryId: Int,
    ) : CategoriesScreenUIEvent()

    data class SetDefaultCategoryIdInDataStore(
        val defaultCategoryId: Int,
        val transactionType: TransactionType,
    ) : CategoriesScreenUIEvent()

    data class UpdateSelectedTabIndex(
        val updatedSelectedTabIndex: Int,
    ) : CategoriesScreenUIEvent()
}

@Composable
internal fun CategoriesScreenUI(
    uiState: CategoriesScreenUIState,
    state: CommonScreenUIState = rememberCommonScreenUIState(),
    handleUIEvents: (uiEvent: CategoriesScreenUIEvent) -> Unit = {},
) {
    LaunchedEffect(
        key1 = uiState.pagerState.currentPage,
    ) {
        handleUIEvents(CategoriesScreenUIEvent.UpdateSelectedTabIndex(uiState.pagerState.currentPage))
    }

    LaunchedEffect(
        key1 = uiState.selectedTabIndex,
    ) {
        state.coroutineScope.launch {
            uiState.pagerState.animateScrollToPage(uiState.selectedTabIndex)
        }
    }

    BottomSheetHandler(
        showModalBottomSheet = uiState.categoriesBottomSheetType != CategoriesBottomSheetType.None,
        bottomSheetType = uiState.categoriesBottomSheetType,
        coroutineScope = state.coroutineScope,
        keyboardController = state.keyboardController,
        modalBottomSheetState = state.modalBottomSheetState,
        resetBottomSheetType = uiState.resetBottomSheetType,
    )

    MyScaffold(
        modifier = Modifier
            .testTag(SCREEN_CATEGORIES)
            .fillMaxSize(),
        sheetContent = {
            when (uiState.categoriesBottomSheetType) {
                is CategoriesBottomSheetType.DeleteConfirmation -> {
                    CategoriesDeleteConfirmationBottomSheet(
                        deleteCategory = {
                            uiState.categoryIdToDelete?.let { categoryIdToDeleteValue ->
                                handleUIEvents(
                                    CategoriesScreenUIEvent.DeleteCategory(
                                        categoryId = categoryIdToDeleteValue,
                                    )
                                )
                            }
                        },
                        resetBottomSheetType = uiState.resetBottomSheetType,
                    ) {
                        uiState.setCategoryIdToDelete(null)
                    }
                }

                is CategoriesBottomSheetType.None -> {
                    VerticalSpacer()
                }

                is CategoriesBottomSheetType.SetAsDefaultConfirmation -> {
                    CategoriesSetAsDefaultConfirmationBottomSheet(
                        transactionType = uiState.validTransactionTypes[uiState.selectedTabIndex],
                        resetBottomSheetType = uiState.resetBottomSheetType,
                        resetClickedItemId = {
                            uiState.setClickedItemId(null)
                        },
                    ) {
                        uiState.clickedItemId?.let { clickedItemIdValue ->
                            handleUIEvents(
                                CategoriesScreenUIEvent.SetDefaultCategoryIdInDataStore(
                                    defaultCategoryId = clickedItemIdValue,
                                    transactionType = uiState.validTransactionTypes[uiState.selectedTabIndex],
                                )
                            )
                        }
                    }
                }

                is CategoriesBottomSheetType.Menu -> {
                    val bottomSheetData =
                        uiState.categoriesBottomSheetType

                    CategoryMenuBottomSheet(
                        isDeleteVisible = bottomSheetData.isDeleteVisible,
                        isEditVisible = bottomSheetData.isEditVisible,
                        isSetAsDefaultVisible = bottomSheetData.isSetAsDefaultVisible,
                        onDeleteClick = {
                            uiState.setCategoryIdToDelete(bottomSheetData.categoryId)
                            uiState.setCategoriesBottomSheetType(CategoriesBottomSheetType.DeleteConfirmation)
                        },
                        onEditClick = {
                            uiState.resetBottomSheetType()
                            handleUIEvents(
                                CategoriesScreenUIEvent.NavigateToEditCategoryScreen(
                                    categoryId = bottomSheetData.categoryId,
                                )
                            )
                        },
                        onSetAsDefaultClick = {
                            uiState.setClickedItemId(bottomSheetData.categoryId)
                            uiState.setCategoriesBottomSheetType(CategoriesBottomSheetType.SetAsDefaultConfirmation)
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
                    handleUIEvents(CategoriesScreenUIEvent.NavigateUp)
                },
            )
        },
        floatingActionButton = {
            MyFloatingActionButton(
                modifier = Modifier
                    .navigationBarsSpacer(),
                iconImageVector = Icons.Rounded.Add,
                contentDescription = stringResource(
                    id = R.string.screen_categories_floating_action_button_content_description,
                ),
                onClick = {
                    handleUIEvents(
                        CategoriesScreenUIEvent.NavigateToAddCategoryScreen(
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
        onClick = {
            state.focusManager.clearFocus()
        },
        isModalBottomSheetVisible = uiState.categoriesBottomSheetType != CategoriesBottomSheetType.None,
        backHandlerEnabled = uiState.categoriesBottomSheetType != CategoriesBottomSheetType.None,
        coroutineScope = state.coroutineScope,
        onBackPress = uiState.resetBottomSheetType,
    ) {
        Column(
            modifier = Modifier
                .testTag(SCREEN_CONTENT_CATEGORIES)
                .fillMaxSize()
                .navigationBarLandscapeSpacer(),
        ) {
            MyTabRow(
                selectedTabIndex = uiState.selectedTabIndex,
                updateSelectedTabIndex = { updatedSelectedTabIndex ->
                    handleUIEvents(
                        CategoriesScreenUIEvent.UpdateSelectedTabIndex(
                            updatedSelectedTabIndex = updatedSelectedTabIndex,
                        )
                    )
                },
                tabData = uiState.tabData,
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
                        val isDeleteVisible =
                            categoriesGridItemDataList[index].isDeleteVisible ?: false
                        val isEditVisible = categoriesGridItemDataList[index].isEditVisible ?: false
                        val isSetAsDefaultVisible =
                            categoriesGridItemDataList[index].isSetAsDefaultVisible ?: false

                        if (isEditVisible || isSetAsDefaultVisible || isDeleteVisible) {
                            uiState.setCategoriesBottomSheetType(
                                CategoriesBottomSheetType.Menu(
                                    isDeleteVisible = isDeleteVisible,
                                    isEditVisible = isEditVisible,
                                    isSetAsDefaultVisible = isSetAsDefaultVisible,
                                    categoryId = categoriesGridItemDataList[index].category.id,
                                )
                            )
                            uiState.setClickedItemId(categoriesGridItemDataList[index].category.id)
                        }
                    },
                )
            }
        }
    }
}

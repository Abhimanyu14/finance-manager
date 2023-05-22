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
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyTabData
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyTabRow
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.buttons.MyFloatingActionButton
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.BottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.CommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.toggleModalBottomSheetState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.grid.CategoriesGrid
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.grid_item.CategoriesGridItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.component.bottomsheet.CategoriesDeleteConfirmationBottomSheetContent
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.component.bottomsheet.CategoriesSetAsDefaultConfirmationBottomSheetContent
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.component.bottomsheet.CategoryMenuBottomSheetContent
import kotlinx.coroutines.launch

@Immutable
internal sealed class CategoriesBottomSheetType : BottomSheetType {
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
internal data class CategoriesScreenViewData(
    val selectedTabIndex: Int,
    val categoriesGridItemDataMap: Map<TransactionType, List<CategoriesGridItemData>>,
)

@Immutable
internal data class CategoriesScreenViewEvents(
    val deleteCategory: (categoryId: Int) -> Unit,
    val navigateToAddCategoryScreen: (transactionType: String) -> Unit,
    val navigateToEditCategoryScreen: (categoryId: Int) -> Unit,
    val navigateUp: () -> Unit,
    val setDefaultCategoryIdInDataStore: (
        defaultCategoryId: Int,
        transactionType: TransactionType,
    ) -> Unit,
    val updateSelectedTabIndex: (updatedSelectedTabIndex: Int) -> Unit,
)

@Composable
internal fun CategoriesScreenView(
    data: CategoriesScreenViewData,
    events: CategoriesScreenViewEvents,
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
        events.updateSelectedTabIndex(pagerState.currentPage)
    }

    LaunchedEffect(
        key1 = data.selectedTabIndex,
    ) {
        state.coroutineScope.launch {
            pagerState.animateScrollToPage(data.selectedTabIndex)
        }
    }

    if (state.modalBottomSheetState.currentValue != ModalBottomSheetValue.Hidden) {
        DisposableEffect(
            key1 = Unit,
        ) {
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
                        deleteCategory = {
                            categoryIdToDelete?.let { categoryIdToDeleteValue ->
                                events.deleteCategory(categoryIdToDeleteValue)
                            }
                        },
                        resetBottomSheetType = resetBottomSheetType,
                        resetCategoryIdToDelete = {
                            categoryIdToDelete = null
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
                        resetBottomSheetType = resetBottomSheetType,
                        resetClickedItemId = {
                            clickedItemId = null
                        },
                        setDefaultCategoryIdInDataStore = {
                            clickedItemId?.let { clickedItemIdValue ->
                                events.setDefaultCategoryIdInDataStore(
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
                        isDeleteVisible = bottomSheetData.isDeleteVisible,
                        isEditVisible = bottomSheetData.isEditVisible,
                        isSetAsDefaultVisible = bottomSheetData.isSetAsDefaultVisible,
                        onDeleteClick = {
                            categoryIdToDelete = bottomSheetData.categoryId
                            categoriesBottomSheetType =
                                CategoriesBottomSheetType.DeleteConfirmation
                        },
                        onEditClick = {
                            toggleModalBottomSheetState(
                                coroutineScope = state.coroutineScope,
                                modalBottomSheetState = state.modalBottomSheetState,
                            ) {
                                resetBottomSheetType()
                                events.navigateToEditCategoryScreen(bottomSheetData.categoryId)
                            }
                        },
                        onSetAsDefaultClick = {
                            clickedItemId = bottomSheetData.categoryId
                            categoriesBottomSheetType =
                                CategoriesBottomSheetType.SetAsDefaultConfirmation
                        },
                    )
                }
            }
        },
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.screen_categories_appbar_title,
                navigationAction = events.navigateUp,
            )
        },
        floatingActionButton = {
            MyFloatingActionButton(
                iconImageVector = Icons.Rounded.Add,
                contentDescription = stringResource(
                    id = R.string.screen_categories_floating_action_button_content_description,
                ),
                onClick = {
                    events.navigateToAddCategoryScreen(
                        when (data.selectedTabIndex) {
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
                updateSelectedTabIndex = events.updateSelectedTabIndex,
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
                    data.categoriesGridItemDataMap[transactionType] ?: emptyList()

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
                            categoriesBottomSheetType = CategoriesBottomSheetType.Menu(
                                isDeleteVisible = isDeleteVisible,
                                isEditVisible = isEditVisible,
                                isSetAsDefaultVisible = isSetAsDefaultVisible,
                                categoryId = categoriesGridItemDataList[index].category.id,
                            )
                            clickedItemId = categoriesGridItemDataList[index].category.id
                            toggleModalBottomSheetState(
                                coroutineScope = state.coroutineScope,
                                modalBottomSheetState = state.modalBottomSheetState,
                            )
                        }
                    },
                )
            }
        }
    }
}

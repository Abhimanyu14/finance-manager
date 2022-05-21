package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.categories

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.makeappssimple.abhimanyu.financemanager.android.R
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateToAddCategoryScreen
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateToEditCategoryScreen
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MyFloatingActionButton
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.ScaffoldContentWrapper
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.toggleModalBottomSheetState
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.MyText
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.sources.SourcesSetAsDefaultBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.sources.SourcesSetAsDefaultBottomSheetData
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.BottomSheetShape
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Primary
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Surface
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.utils.isDefaultCategory
import com.makeappssimple.abhimanyu.financemanager.android.utils.isSalaryCategory
import kotlinx.coroutines.launch

enum class CategoriesBottomSheetType {
    NONE,
    SET_AS_DEFAULT_CONFIRMATION,
}

data class CategoriesScreenViewData(
    val screenViewModel: CategoriesScreenViewModel,
)

@OptIn(
    ExperimentalComposeUiApi::class,
    ExperimentalMaterial3Api::class,
    ExperimentalPagerApi::class,
)
@ExperimentalMaterialApi
@Composable
fun CategoriesScreenView(
    data: CategoriesScreenViewData,
    state: CategoriesScreenViewState,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val coroutineScope = rememberCoroutineScope()
    val selectedTabIndex by data.screenViewModel.selectedTabIndex.collectAsState()
    val expenseCategories by data.screenViewModel.expenseCategories.collectAsState(
        initial = emptyList(),
    )
    val incomeCategories by data.screenViewModel.incomeCategories.collectAsState(
        initial = emptyList(),
    )
    val expenseCategoryIsUsedInTransactions by data.screenViewModel.expenseCategoryIsUsedInTransactions.collectAsState(
        initial = emptyList(),
    )
    val incomeCategoryIsUsedInTransactions by data.screenViewModel.incomeCategoryIsUsedInTransactions.collectAsState(
        initial = emptyList(),
    )
    val defaultExpenseCategoryId by data.screenViewModel.defaultExpenseCategoryId.collectAsState(
        initial = null,
    )
    val defaultIncomeCategoryId by data.screenViewModel.defaultIncomeCategoryId.collectAsState(
        initial = null,
    )
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
    val transactionTypes = listOf(
        TransactionType.EXPENSE,
        TransactionType.INCOME,
    )

    LaunchedEffect(
        key1 = pagerState.currentPage,
    ) {
        data.screenViewModel.updateSelectedTabIndex(
            updatedSelectedTabIndex = pagerState.currentPage,
        )
    }
    LaunchedEffect(
        key1 = selectedTabIndex,
    ) {
        coroutineScope.launch {
            pagerState.animateScrollToPage(selectedTabIndex)
        }
    }

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
                                    clickedItemId?.let { clickedItemIdValue ->
                                        data.screenViewModel.setDefaultCategoryIdInDataStore(
                                            defaultCategoryId = clickedItemIdValue,
                                            transactionType = transactionType,
                                        )
                                    }
                                    categoriesBottomSheetType = CategoriesBottomSheetType.NONE
                                    clickedItemId = null
                                }
                            },
                            onNegativeButtonClick = {
                                toggleModalBottomSheetState(
                                    coroutineScope = state.coroutineScope,
                                    modalBottomSheetState = state.modalBottomSheetState,
                                ) {
                                    categoriesBottomSheetType = CategoriesBottomSheetType.NONE
                                    clickedItemId = null
                                }
                            },
                        ),
                    )
                }
            }
        },
    ) {
        Scaffold(
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
                    TabRow(
                        selectedTabIndex = selectedTabIndex,
                        containerColor = Surface,
                        contentColor = Primary,
                    ) {
                        transactionTypes
                            .map {
                                it.title
                            }
                            .forEachIndexed { index, title ->
                                Tab(
                                    text = {
                                        MyText(
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
                                    },
                                    selectedContentColor = Primary,
                                    unselectedContentColor = Primary,
                                )
                            }
                    }
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
                                    expenseCategories
                                } else {
                                    incomeCategories
                                },
                                key = { _, listItem ->
                                    listItem.hashCode()
                                },
                            ) { index, listItem ->
                                val deleteEnabled: Boolean? =
                                    if (transactionType == TransactionType.EXPENSE) {
                                        expenseCategoryIsUsedInTransactions.getOrNull(
                                            index = index,
                                        )?.not()
                                    } else {
                                        incomeCategoryIsUsedInTransactions.getOrNull(
                                            index = index,
                                        )?.not()
                                    }
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
                                            navigationManager = data.screenViewModel.navigationManager,
                                            categoryId = listItem.id,
                                        )
                                        expenseExpandedItemIndex = null
                                        incomeExpandedItemIndex = null
                                    },
                                    onDeleteClick = {
                                        data.screenViewModel.deleteCategory(
                                            id = listItem.id,
                                        )
                                        expenseExpandedItemIndex = null
                                        incomeExpandedItemIndex = null
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

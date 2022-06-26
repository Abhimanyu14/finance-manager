package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.transactions.screen

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import com.makeappssimple.abhimanyu.financemanager.android.entities.category.Category
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.Source
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.transactions.components.TransactionsListItemViewData
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.transactions.viewmodel.TransactionsScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.transactions.viewmodel.TransactionsScreenViewModelImpl
import com.makeappssimple.abhimanyu.financemanager.android.utils.logError

@OptIn(
    ExperimentalComposeUiApi::class,
    ExperimentalMaterialApi::class,
)
@Composable
fun TransactionsScreen(
    screenViewModel: TransactionsScreenViewModel = hiltViewModel<TransactionsScreenViewModelImpl>(),
) {
    logError(
        message = "Inside TransactionsScreen",
    )
    val categories: List<Category> by screenViewModel.categories.collectAsState(
        initial = emptyList(),
    )
    val sources: List<Source> by screenViewModel.sources.collectAsState(
        initial = emptyList(),
    )
    val transactionsListItemViewData: List<TransactionsListItemViewData> by screenViewModel
        .transactionsListItemViewData.collectAsState(
            initial = emptyList(),
        )

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.trackScreen()
    }

    TransactionsScreenView(
        data = TransactionsScreenViewData(
            categories = categories,
            sources = sources,
            transactionsListItemViewData = transactionsListItemViewData,
            navigationManager = screenViewModel.navigationManager,
            deleteTransaction = { transactionId ->
                screenViewModel.deleteTransaction(
                    id = transactionId,
                )
            },
        ),
        state = rememberTransactionsScreenViewState(),
    )
}

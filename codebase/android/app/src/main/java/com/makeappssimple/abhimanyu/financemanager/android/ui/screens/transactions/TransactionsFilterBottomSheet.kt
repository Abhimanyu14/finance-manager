package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.transactions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.makeappssimple.abhimanyu.financemanager.android.R
import com.makeappssimple.abhimanyu.financemanager.android.entities.category.Category
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.Source
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MyRadioGroupItem
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MySelectionGroup
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.VerticalSpacer

data class TransactionsFilterBottomSheetSelectionData(
    val selectedCategoryIndices: List<Int>,
    val selectedSourceIndices: List<Int>,
    val selectedTransactionTypeIndices: List<Int>
)

data class TransactionsFilterBottomSheetData(
    val categories: List<Category>,
    val sources: List<Source>,
    val transactionTypes: List<TransactionType>,
    val selectedCategoryIndices: List<Int>,
    val selectedSourceIndices: List<Int>,
    val selectedTransactionTypesIndices: List<Int>,
    val onPositiveButtonClick: (data: TransactionsFilterBottomSheetSelectionData) -> Unit,
    val onNegativeButtonClick: () -> Unit,
)

@Composable
fun TransactionsFiltersBottomSheet(
    data: TransactionsFilterBottomSheetData,
) {
    val selectedCategoryIndices = remember {
        mutableStateListOf(
            *data.selectedCategoryIndices.toTypedArray(),
        )
    }
    val selectedSourceIndices = remember {
        mutableStateListOf(
            *data.selectedSourceIndices.toTypedArray(),
        )
    }
    val selectedTransactionTypesIndices = remember {
        mutableStateListOf(
            *data.selectedTransactionTypesIndices.toTypedArray(),
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .defaultMinSize(
                minHeight = 24.dp,
            ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(
                    weight = 1F,
                )
                .verticalScroll(
                    state = rememberScrollState(),
                ),
        ) {
            VerticalSpacer(
                height = 16.dp,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Text(
                    text = stringResource(
                        id = R.string.bottom_sheet_transactions_filter_select_categories,
                    ),
                    style = TextStyle(
                        color = Color.DarkGray,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    ),
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .weight(
                            weight = 1F,
                        )
                        .padding(
                            all = 16.dp,
                        ),
                )
                TextButton(
                    onClick = {
                        selectedCategoryIndices.clear()
                    },
                    modifier = Modifier
                        .padding(
                            horizontal = 16.dp,
                        ),
                ) {
                    Text(
                        text = stringResource(
                            id = R.string.bottom_sheet_transactions_filter_clear,
                        ),
                    )
                }
            }
            MySelectionGroup(
                items = data.categories
                    .map { category ->
                        MyRadioGroupItem(
                            text = category.title,
                        )
                    },
                selectedItemsIndices = selectedCategoryIndices,
                onSelectionChange = { index ->
                    if (selectedCategoryIndices.contains(index)) {
                        selectedCategoryIndices.remove(index)
                    } else {
                        selectedCategoryIndices.add(index)
                    }
                },
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp,
                        vertical = 4.dp,
                    ),
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Text(
                    text = stringResource(
                        id = R.string.bottom_sheet_transactions_filter_select_sources,
                    ),
                    style = TextStyle(
                        color = Color.DarkGray,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    ),
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .weight(
                            weight = 1F,
                        )
                        .padding(
                            all = 16.dp,
                        ),
                )
                TextButton(
                    onClick = {
                        selectedSourceIndices.clear()
                    },
                    modifier = Modifier
                        .padding(
                            horizontal = 16.dp,
                        ),
                ) {
                    Text(
                        text = stringResource(
                            id = R.string.bottom_sheet_transactions_filter_clear,
                        ),
                    )
                }
            }
            MySelectionGroup(
                items = data.sources
                    .map { source ->
                        MyRadioGroupItem(
                            text = source.name,
                        )
                    },
                selectedItemsIndices = selectedSourceIndices,
                onSelectionChange = { index ->
                    if (selectedSourceIndices.contains(index)) {
                        selectedSourceIndices.remove(index)
                    } else {
                        selectedSourceIndices.add(index)
                    }
                },
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp,
                        vertical = 4.dp,
                    ),
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Text(
                    text = stringResource(
                        id = R.string.bottom_sheet_transactions_filter_select_transaction_types,
                    ),
                    style = TextStyle(
                        color = Color.DarkGray,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    ),
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .weight(
                            weight = 1F,
                        )
                        .padding(
                            all = 16.dp,
                        ),
                )
                TextButton(
                    onClick = {
                        selectedTransactionTypesIndices.clear()
                    },
                    modifier = Modifier
                        .padding(
                            horizontal = 16.dp,
                        ),
                ) {
                    Text(
                        text = stringResource(
                            id = R.string.bottom_sheet_transactions_filter_clear,
                        ),
                    )
                }
            }
            MySelectionGroup(
                items = data.transactionTypes
                    .map { transactionType ->
                        MyRadioGroupItem(
                            text = transactionType.title,
                        )
                    },
                selectedItemsIndices = selectedTransactionTypesIndices,
                onSelectionChange = { index ->
                    if (selectedTransactionTypesIndices.contains(index)) {
                        selectedTransactionTypesIndices.remove(index)
                    } else {
                        selectedTransactionTypesIndices.add(index)
                    }
                },
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp,
                        vertical = 4.dp,
                    ),
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OutlinedButton(
                onClick = {
                    selectedCategoryIndices.clear()
                    selectedSourceIndices.clear()
                    selectedTransactionTypesIndices.clear()
                    data.onNegativeButtonClick()
                },
                modifier = Modifier
                    .padding(
                        all = 16.dp,
                    )
                    .weight(
                        weight = 1F,
                    ),
            ) {
                Text(
                    text = stringResource(
                        id = R.string.bottom_sheet_transactions_filter_reset,
                    ),
                )
            }
            Button(
                onClick = {
                    data.onPositiveButtonClick(
                        TransactionsFilterBottomSheetSelectionData(
                            selectedCategoryIndices = selectedCategoryIndices,
                            selectedSourceIndices = selectedSourceIndices,
                            selectedTransactionTypeIndices = selectedTransactionTypesIndices,
                        )
                    )
                },
                modifier = Modifier
                    .padding(
                        all = 16.dp,
                    )
                    .weight(
                        weight = 1F,
                    ),
            ) {
                Text(
                    text = stringResource(
                        id = R.string.bottom_sheet_transactions_filter_apply,
                    ),
                )
            }
        }
    }
}

package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.component.bottomsheet

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.formattedDate
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.getMyDatePickerDialog
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfields.MyReadOnlyTextField
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.viewmodel.Filter
import java.time.LocalDate

@Composable
fun AnalysisFilterBottomSheetDateFilter(
    context: Context,
    selectedFilter: Filter,
    @StringRes headingTextStringResourceId: Int,
    currentTimeMillis: Long,
    minDate: LocalDate,
    maxDate: LocalDate,
    onPositiveButtonClick: (filter: Filter) -> Unit,
    onNegativeButtonClick: () -> Unit,
) {
    var fromDate by remember {
        mutableStateOf(
            value = selectedFilter.fromDate ?: minDate,
        )
    }
    var toDate by remember {
        mutableStateOf(
            value = selectedFilter.toDate ?: maxDate,
        )
    }
    val fromDatePickerDialog = getMyDatePickerDialog(
        context = context,
        selectedDate = fromDate,
        minDate = minDate,
        maxDate = toDate,
        currentTimeMillis = currentTimeMillis,
        onDateSetListener = {
            fromDate = it
        },
    )
    val toDatePickerDialog = getMyDatePickerDialog(
        context = context,
        selectedDate = toDate,
        minDate = fromDate,
        maxDate = maxDate,
        currentTimeMillis = currentTimeMillis,
        onDateSetListener = {
            toDate = it
        },
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(
                    start = 24.dp,
                    end = 24.dp,
                    top = 16.dp,
                )
                .fillMaxWidth(),
        ) {
            MyText(
                modifier = Modifier
                    .weight(
                        weight = 1F,
                    ),
                textStringResourceId = headingTextStringResourceId,
                style = MaterialTheme.typography.headlineLarge
                    .copy(
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Start,
                    ),
            )
            TextButton(
                onClick = {
                    fromDate = minDate
                    toDate = maxDate
                },
                modifier = Modifier,
            ) {
                MyText(
                    textStringResourceId = R.string.bottom_sheet_analysis_filter_clear,
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 4.dp,
                    bottom = 16.dp,
                ),
        ) {
            MyReadOnlyTextField(
                value = fromDate.formattedDate(),
                labelTextStringResourceId = R.string.bottom_sheet_analysis_filter_from_date,
                onClick = {
                    fromDatePickerDialog.show()
                },
                modifier = Modifier
                    .weight(
                        weight = 1F,
                    )
                    .padding(
                        horizontal = 8.dp,
                    ),
            )
            MyReadOnlyTextField(
                value = toDate.formattedDate(),
                labelTextStringResourceId = R.string.bottom_sheet_analysis_filter_to_date,
                onClick = {
                    toDatePickerDialog.show()
                },
                modifier = Modifier
                    .weight(
                        weight = 1F,
                    )
                    .padding(
                        horizontal = 8.dp,
                    ),
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OutlinedButton(
                modifier = Modifier
                    .padding(
                        all = 16.dp,
                    )
                    .weight(
                        weight = 1F,
                    ),
                onClick = {
                    fromDate = minDate
                    toDate = maxDate
                    onNegativeButtonClick()
                },
            ) {
                MyText(
                    textStringResourceId = R.string.bottom_sheet_analysis_filter_reset,
                    style = MaterialTheme.typography.labelLarge,
                )
            }
            Button(
                modifier = Modifier
                    .padding(
                        all = 16.dp,
                    )
                    .weight(
                        weight = 1F,
                    ),
                onClick = {
                    val isFromDateSameAsOldestTransactionDate = fromDate == minDate
                    val isToDateSameAsCurrentDayDate = toDate == maxDate
                    val isDateFilterCleared = isFromDateSameAsOldestTransactionDate &&
                            isToDateSameAsCurrentDayDate
                    onPositiveButtonClick(
                        Filter(
                            fromDate = fromDate,
                            toDate = if (isDateFilterCleared) {
                                null
                            } else {
                                toDate
                            },
                        )
                    )
                },
            ) {
                MyText(
                    textStringResourceId = R.string.bottom_sheet_analysis_filter_apply,
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }
    }
}

package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.component.bottomsheet

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyHorizontalScrollingSelectionGroup
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.datepicker.MyDatePicker
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.datepicker.MyDatePickerData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.datepicker.MyDatePickerEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfields.MyReadOnlyTextField
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.R
import java.time.LocalDate

@Composable
fun AnalysisFilterBottomSheetUI(
    modifier: Modifier = Modifier,
    isFromDatePickerDialogVisible: Boolean,
    isToDatePickerDialogVisible: Boolean,
    headingTextStringResourceId: Int,
    fromDatePickerEndLocalDate: LocalDate,
    fromDatePickerSelectedLocalDate: LocalDate,
    fromDatePickerStartLocalDate: LocalDate,
    toDatePickerEndLocalDate: LocalDate,
    toDatePickerSelectedLocalDate: LocalDate,
    toDatePickerStartLocalDate: LocalDate,
    fromDateText: String,
    toDateText: String,
    onClearButtonClick: () -> Unit,
    onDateRangeOptionClick: (dateRangeOption: DateRangeOptions) -> Unit,
    onFromDateSelected: (LocalDate) -> Unit,
    onFromDateTextFieldClick: () -> Unit,
    onNegativeButtonClick: () -> Unit,
    onPositiveButtonClick: () -> Unit,
    onToDateSelected: (LocalDate) -> Unit,
    onToDateTextFieldClick: () -> Unit,
    setFromDatePickerDialogVisible: (Boolean) -> Unit,
    setToDatePickerDialogVisible: (Boolean) -> Unit,
) {
    MyDatePicker(
        data = MyDatePickerData(
            isVisible = isFromDatePickerDialogVisible,
            endLocalDate = fromDatePickerEndLocalDate,
            selectedLocalDate = fromDatePickerSelectedLocalDate,
            startLocalDate = fromDatePickerStartLocalDate,
        ),
        events = MyDatePickerEvents(
            onPositiveButtonClick = {
                onFromDateSelected(it)
                setFromDatePickerDialogVisible(false)
            },
            onNegativeButtonClick = {
                setFromDatePickerDialogVisible(false)
            },
        )
    )
    MyDatePicker(
        data = MyDatePickerData(
            isVisible = isToDatePickerDialogVisible,
            endLocalDate = toDatePickerEndLocalDate,
            selectedLocalDate = toDatePickerSelectedLocalDate,
            startLocalDate = toDatePickerStartLocalDate,
        ),
        events = MyDatePickerEvents(
            onPositiveButtonClick = {
                onToDateSelected(it)
                setToDatePickerDialogVisible(false)
            },
            onNegativeButtonClick = {
                setToDatePickerDialogVisible(false)
            },
        )
    )
    Column(
        modifier = modifier
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
                onClick = onClearButtonClick,
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
                value = fromDateText,
                labelTextStringResourceId = R.string.bottom_sheet_analysis_filter_from_date,
                onClick = onFromDateTextFieldClick,
                modifier = Modifier
                    .weight(
                        weight = 1F,
                    )
                    .padding(
                        horizontal = 8.dp,
                    ),
            )
            MyReadOnlyTextField(
                value = toDateText,
                labelTextStringResourceId = R.string.bottom_sheet_analysis_filter_to_date,
                onClick = onToDateTextFieldClick,
                modifier = Modifier
                    .weight(
                        weight = 1F,
                    )
                    .padding(
                        horizontal = 8.dp,
                    ),
            )
        }
        MyHorizontalScrollingSelectionGroup(
            items = DateRangeOptions.values().map {
                ChipUIData(
                    text = it.title,
                )
            },
            onSelectionChange = { index ->
                onDateRangeOptionClick(DateRangeOptions.values()[index])
            },
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                    vertical = 4.dp,
                ),
        )
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
                onClick = onNegativeButtonClick,
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
                onClick = onPositiveButtonClick,
            ) {
                MyText(
                    textStringResourceId = R.string.bottom_sheet_analysis_filter_apply,
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }
    }
}
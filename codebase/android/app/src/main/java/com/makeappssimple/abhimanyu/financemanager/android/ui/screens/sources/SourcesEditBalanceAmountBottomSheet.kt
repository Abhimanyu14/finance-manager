package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.sources

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.makeappssimple.abhimanyu.financemanager.android.R
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.AmountCommaVisualTransformation

data class SourcesEditBalanceAmountBottomSheetData(
    val balanceAmount: Int,
    val updateBalanceAmount: (updatedBalanceAmount: Int) -> Unit,
)

@Composable
fun SourcesEditBalanceAmountBottomSheet(
    data: SourcesEditBalanceAmountBottomSheetData,
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember {
        FocusRequester()
    }
    var updatedBalanceAmount by remember {
        mutableStateOf(
            value = TextFieldValue(
                text = data.balanceAmount.toString(),
                selection = TextRange(
                    start = data.balanceAmount.toString().length,
                    end = data.balanceAmount.toString().length,
                ),
            ),
        )
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        focusRequester.requestFocus()
    }

    Column(
        modifier = Modifier
            .padding(
                all = 16.dp,
            )
            .fillMaxWidth(),
    ) {
        Text(
            text = stringResource(
                id = R.string.bottom_sheet_sources_edit_balance_amount_title,
            ),
            style = TextStyle(
                color = Color.DarkGray,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
            ),
            modifier = Modifier
                .fillMaxWidth(),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BasicTextField(
                value = updatedBalanceAmount,
                onValueChange = { value: TextFieldValue ->
                    updatedBalanceAmount = value
                },
                visualTransformation = AmountCommaVisualTransformation(),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    },
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword,
                    imeAction = ImeAction.Done,
                ),
                modifier = Modifier
                    .weight(
                        weight = 1F,
                    )
                    .focusRequester(
                        focusRequester = focusRequester,
                    ),
                singleLine = true,
            )
            TextButton(
                onClick = {
                    val balanceAmount = if (updatedBalanceAmount.text.isNotBlank()) {
                        updatedBalanceAmount.text.toInt()
                    } else {
                        0
                    }
                    data.updateBalanceAmount(
                        balanceAmount
                    )
                },
            ) {
                Text(
                    text = stringResource(
                        id = R.string.bottom_sheet_sources_edit_balance_amount_save,
                    ),
                )
            }
        }
    }
}

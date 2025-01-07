package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.view_transaction_section_header

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.cre.designsystem.component.MyText

@Composable
public fun ViewTransactionSectionHeader(
    modifier: Modifier = Modifier,
    data: ViewTransactionSectionHeaderData,
) {
    MyText(
        modifier = modifier
            .padding(
                top = 16.dp,
                start = 16.dp,
            )
            .fillMaxWidth(),
        textStringResourceId = data.textStringResourceId,
        style = MaterialTheme.typography.headlineMedium
            .copy(
                color = MaterialTheme.colorScheme.onBackground,
            ),
    )
}

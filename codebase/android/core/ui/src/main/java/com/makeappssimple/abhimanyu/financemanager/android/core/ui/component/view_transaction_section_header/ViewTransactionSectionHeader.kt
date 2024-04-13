package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.view_transaction_section_header

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText

@Composable
public fun ViewTransactionSectionHeader(
    modifier: Modifier = Modifier,
    @StringRes textStringResourceId: Int,
) {
    MyText(
        modifier = modifier
            .padding(
                top = 16.dp,
                start = 16.dp,
            )
            .fillMaxWidth(),
        textStringResourceId = textStringResourceId,
        style = MaterialTheme.typography.headlineMedium
            .copy(
                color = MaterialTheme.colorScheme.onBackground,
            ),
    )
}

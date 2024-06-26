package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText

@Composable
public fun MyBottomSheetTitle(
    data: MyBottomSheetTitleData,
    modifier: Modifier = Modifier,
) {
    MyText(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.background,
            )
            .padding(
                all = 16.dp,
            ),
        textStringResourceId = data.textStringResourceId,
        style = MaterialTheme.typography.headlineLarge
            .copy(
                color = MaterialTheme.colorScheme.onBackground,
            ),
    )
}

package com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.bottom_sheet

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.DarkGray

@Composable
fun BottomSheetTitle(
    @StringRes textStringResourceId: Int,
) {
    MyText(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
            )
            .padding(
                all = 16.dp,
            ),
        textStringResourceId = textStringResourceId,
        style = TextStyle(
            color = DarkGray,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
        ),
    )
}

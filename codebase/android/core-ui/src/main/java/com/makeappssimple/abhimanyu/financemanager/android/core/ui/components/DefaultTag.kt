package com.makeappssimple.abhimanyu.financemanager.android.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.White

@Composable
fun DefaultTag() {
    MyText(
        modifier = Modifier
            .clip(
                shape = RoundedCornerShape(
                    size = 8.dp,
                )
            )
            .background(
                color = MaterialTheme.colorScheme.primary,
            )
            .padding(
                vertical = 2.dp,
                horizontal = 8.dp,
            ),
        text = "Default",
        style = TextStyle(
            color = White,
            fontSize = 10.sp,
            fontWeight = FontWeight.Normal,
        ),
    )
}

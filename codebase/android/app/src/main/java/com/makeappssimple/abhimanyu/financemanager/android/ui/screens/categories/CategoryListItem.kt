package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.categories

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.makeappssimple.abhimanyu.financemanager.android.entities.category.Category
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Surface

@Composable
fun CategoryListItem(
    category: Category,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Surface,
            )
            .padding(
                horizontal = 16.dp,
                vertical = 12.dp,
            ),
    ) {
        Box(
            modifier = Modifier
                .clip(
                    shape = CircleShape,
                )
                .background(
                    color = Color.LightGray,
                )
                .padding(
                    all = 2.dp,
                ),
        ) {
            AndroidView(
                factory = { context ->
                    AppCompatTextView(context).apply {
                        setTextColor(Color.Black.toArgb())
                        text = category.emoji
                        textSize = 20F
                        textAlignment = View.TEXT_ALIGNMENT_CENTER
                    }
                },
                update = {
                    it.apply {
                        text = category.emoji
                    }
                },
            )
        }
        Text(
            text = category.title,
            style = TextStyle(
                color = Color.DarkGray,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
            ),
            modifier = Modifier
                .padding(
                    start = 8.dp,
                ),
        )
    }
}

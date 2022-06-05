package com.makeappssimple.abhimanyu.financemanager.android.ui.components

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Black
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.LightGray

@Composable
fun EmojiCircle(
    emoji: String?,
) {
    Box(
        modifier = Modifier
            .clip(
                shape = CircleShape,
            )
            .background(
                color = LightGray,
            )
            .padding(
                all = 2.dp,
            ),
    ) {
        AndroidView(
            factory = { context ->
                AppCompatTextView(context).apply {
                    setTextColor(Black.toArgb())
                    text = emoji ?: "😟"
                    textSize = 20F
                    textAlignment = View.TEXT_ALIGNMENT_CENTER
                }
            },
            update = {
                it.apply {
                    text = emoji ?: "😟"
                }
            },
        )
    }
}

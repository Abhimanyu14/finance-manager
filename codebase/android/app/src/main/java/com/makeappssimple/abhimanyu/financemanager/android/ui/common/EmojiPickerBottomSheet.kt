package com.makeappssimple.abhimanyu.financemanager.android.ui.common

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.accompanist.flowlayout.FlowRow
import com.makeappssimple.abhimanyu.financemanager.android.data.remote.emoji.Emoji

data class EmojiPickerBottomSheetItemData(
    val emoji: Emoji,
    val onEmojiSelection: (emoji: Emoji) -> Unit,
)

data class EmojiPickerBottomSheetData(
    val emojis: List<Emoji>,
    val onEmojiSelection: (emoji: Emoji) -> Unit,
)

@Composable
fun EmojiPickerBottomSheet(
    data: EmojiPickerBottomSheetData,
) {
    val grouped = data.emojis.groupBy {
        it.group
    }

    Column(
        modifier = Modifier
            .verticalScroll(
                state = rememberScrollState(),
            )
            .defaultMinSize(
                minHeight = 100.dp,
            ),
    ) {
        grouped.forEach { (group, emojis) ->
            GroupName(
                name = "$group (${emojis.size})"
            )
            FlowRow {
                emojis.forEachIndexed { index, item ->
                    EmojiPickerBottomSheetItem(
                        data = EmojiPickerBottomSheetItemData(
                            emoji = item,
                            onEmojiSelection = data.onEmojiSelection,
                        ),
                    )
                }
            }
        }
    }
}

@Composable
private fun EmojiPickerBottomSheetItem(
    data: EmojiPickerBottomSheetItemData,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .clip(
                shape = CircleShape,
            )
            .clickable {
                data.onEmojiSelection(data.emoji)
            }
            .padding(
                all = 4.dp,
            ),
    ) {
        AndroidView(
            factory = { context ->
                AppCompatTextView(context).apply {
                    setTextColor(Color.Black.toArgb())
                    text = data.emoji.character
                    textSize = 28F
                    textAlignment = View.TEXT_ALIGNMENT_CENTER
                }
            },
        )
    }
}

@Composable
private fun GroupName(
    name: String,
) {
    Text(
        text = name,
        modifier = Modifier
            .padding(
                all = 8.dp,
            ),
    )
}

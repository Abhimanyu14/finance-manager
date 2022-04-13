package com.makeappssimple.abhimanyu.financemanager.android.ui.common

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.viewinterop.AndroidView
import com.google.accompanist.flowlayout.FlowRow
import com.makeappssimple.abhimanyu.financemanager.android.entities.emoji.Emoji
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.capitalizeWords

data class EmojiPickerBottomSheetItemData(
    val emoji: Emoji,
    val onEmojiSelection: (emoji: Emoji) -> Unit,
    val onEmojiLongClick: (emoji: Emoji) -> Unit,
)

data class EmojiPickerBottomSheetData(
    val emojis: List<Emoji>,
    val onEmojiSelection: (emoji: Emoji) -> Unit,
    val onEmojiLongClick: (emoji: Emoji) -> Unit,
)

@Composable
fun EmojiPickerBottomSheet(
    data: EmojiPickerBottomSheetData,
) {
    val grouped: Map<String, List<Emoji>> = data.emojis.groupBy {
        it.group
    }

    LazyColumn(
        modifier = Modifier
            .defaultMinSize(
                minHeight = 24.dp,
            ),
    ) {
        grouped.forEach { (group, emojis) ->
            item {
                EmojiGroupName(
                    name = "$group (${emojis.size})"
                )
            }
            item {
                FlowRow {
                    emojis.forEachIndexed { _, item ->
                        EmojiPickerBottomSheetItem(
                            data = EmojiPickerBottomSheetItemData(
                                emoji = item,
                                onEmojiSelection = data.onEmojiSelection,
                                onEmojiLongClick = data.onEmojiLongClick,
                            ),
                        )
                    }
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
            .combinedClickable(
                onClick = {
                    data.onEmojiSelection(data.emoji)
                },
                onLongClick = {
                    data.onEmojiLongClick(data.emoji)
                },
            )
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
private fun EmojiGroupName(
    name: String,
) {
    Text(
        text = name
            .replace(
                oldChar = '-',
                newChar = ' ',
            )
            .capitalizeWords(),
        style = TextStyle(
            fontWeight = FontWeight.Bold,
        ),
        modifier = Modifier
            .padding(
                all = 8.dp,
            ),
    )
}

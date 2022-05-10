package com.makeappssimple.abhimanyu.financemanager.android.ui.common

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.accompanist.flowlayout.FlowRow
import com.makeappssimple.abhimanyu.financemanager.android.R
import com.makeappssimple.abhimanyu.financemanager.android.entities.emoji.Emoji
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Surface
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.capitalizeWords

data class EmojiPickerBottomSheetItemData(
    val emoji: Emoji,
    val onEmojiSelection: (emoji: Emoji) -> Unit,
    val onEmojiLongClick: (emoji: Emoji) -> Unit,
)

data class EmojiPickerBottomSheetData(
    val emojis: List<Emoji>,
    val searchText: String,
    val onEmojiClick: (emoji: Emoji) -> Unit,
    val onEmojiLongClick: (emoji: Emoji) -> Unit,
    val updateSearchText: (updatedSearchText: String) -> Unit,
)

@Composable
fun EmojiPickerBottomSheet(
    data: EmojiPickerBottomSheetData,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(
                minHeight = 24.dp,
            ),
    ) {
        SearchBarContainer {
            SearchBar(
                data = SearchBarData(
                    searchText = data.searchText,
                    placeholderText = stringResource(
                        id = R.string.emoji_picker_bottom_sheet_placeholder_text,
                    ),
                    updateSearchText = data.updateSearchText,
                ),
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            data.emojis.groupBy { emoji ->
                emoji.group
            }.forEach { (group, emojis) ->
                item {
                    if (emojis.isNotEmpty()) {
                        EmojiGroupName(
                            name = "$group (${emojis.size})"
                        )
                    }
                }
                item {
                    FlowRow {
                        emojis.forEachIndexed { _, item ->
                            EmojiPickerBottomSheetItem(
                                data = EmojiPickerBottomSheetItemData(
                                    emoji = item,
                                    onEmojiSelection = data.onEmojiClick,
                                    onEmojiLongClick = data.onEmojiLongClick,
                                ),
                            )
                        }
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
            color = Color.DarkGray,
            fontWeight = FontWeight.Bold,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Surface,
            )
            .padding(
                all = 8.dp,
            ),
    )
}

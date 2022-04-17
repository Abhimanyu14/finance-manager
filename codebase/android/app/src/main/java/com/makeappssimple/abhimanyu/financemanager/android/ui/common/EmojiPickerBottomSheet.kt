package com.makeappssimple.abhimanyu.financemanager.android.ui.common

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
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
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Blue100
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
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(
                minHeight = 24.dp,
            ),
    ) {
        stickyHeader {
            Box(
                modifier = Modifier
                    .background(
                        color = Surface,
                    )
                    .fillMaxWidth(),
            ) {
                TextField(
                    value = data.searchText,
                    onValueChange = {
                        data.updateSearchText(it)
                    },
                    shape = RoundedCornerShape(
                        percent = 50,
                    ),
                    singleLine = true,
                    colors = TextFieldDefaults
                        .textFieldColors(
                            backgroundColor = Blue100,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = null,
                        )
                    },
                    trailingIcon = if (data.searchText.isNotBlank()) {
                        {
                            Icon(
                                imageVector = Icons.Rounded.Close,
                                contentDescription = null,
                                modifier = Modifier
                                    .clip(
                                        shape = CircleShape,
                                    )
                                    .clickable {
                                        data.updateSearchText("")
                                    },
                            )
                        }
                    } else {
                        null
                    },
                    placeholder = {
                        Text(
                            text = "Search emoji",
                            style = TextStyle(
                                color = Color.DarkGray,
                                fontWeight = FontWeight.Bold,
                            ),
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = 8.dp,
                            horizontal = 16.dp,
                        ),
                )
            }
        }
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

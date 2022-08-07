package com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.bottom_sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.model.Emoji
import com.makeappssimple.abhimanyu.financemanager.android.core.database.utils.extensions.capitalizeWords
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.R
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.EmojiCircle
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.EmojiCircleSize
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.textfields.MySearchBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.textfields.SearchBarContainer
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.textfields.SearchBarData

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
            MySearchBar(
                data = SearchBarData(
                    searchText = data.searchText,
                    placeholderText = stringResource(
                        id = R.string.emoji_picker_bottom_sheet_placeholder_text,
                    ),
                    onValueChange = data.updateSearchText,
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
                if (emojis.isNotEmpty()) {
                    stickyHeader {
                        EmojiGroupName(
                            name = "$group (${emojis.size})"
                        )
                    }
                    item {
                        FlowRow {
                            emojis.map { emoji ->
                                EmojiCircle(
                                    emoji = emoji.character,
                                    emojiCircleSize = EmojiCircleSize.Normal,
                                    onClick = {
                                        data.onEmojiClick(emoji)
                                    },
                                    onLongClick = {
                                        data.onEmojiLongClick(emoji)
                                    },
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EmojiGroupName(
    name: String,
) {
    MyText(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.background,
            )
            .padding(
                all = 8.dp,
            ),
        text = name
            .replace(
                oldChar = '-',
                newChar = ' ',
            )
            .capitalizeWords(),
        style = MaterialTheme.typography.headlineMedium
            .copy(
                color = MaterialTheme.colorScheme.onBackground,
            ),
    )
}

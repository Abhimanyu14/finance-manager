package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottom_sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.capitalizeWords
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.NavigationBarSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Emoji
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.R
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.EmojiCircleSize
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyEmojiCircle
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyEmojiCircleData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyEmojiCircleEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfields.MySearchBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfields.MySearchBarContainer
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfields.MySearchBarData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfields.MySearchBarEvents

@Composable
fun MyEmojiPickerBottomSheet(
    modifier: Modifier = Modifier,
    emojiGroups: Map<String, List<Emoji>>,
    searchText: String,
    onEmojiClick: (emoji: Emoji) -> Unit,
    onEmojiLongClick: (emoji: Emoji) -> Unit,
    updateSearchText: (updatedSearchText: String) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(
                minHeight = 24.dp,
            )
            .statusBarsPadding(),
    ) {
        MySearchBarContainer {
            MySearchBar(
                data = MySearchBarData(
                    placeholderText = stringResource(
                        id = R.string.emoji_picker_bottom_sheet_placeholder_text,
                    ),
                    searchText = searchText,
                ),
                events = MySearchBarEvents(
                    onValueChange = updateSearchText,
                ),
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            emojiGroups.map { (group, emojis) ->
                stickyHeader {
                    EmojiGroupName(
                        name = "$group (${emojis.size})"
                    )
                }
                item {
                    FlowRow {
                        emojis.map { emoji ->
                            MyEmojiCircle(
                                data = MyEmojiCircleData(
                                    emojiCircleSize = EmojiCircleSize.Normal,
                                    emoji = emoji.character,
                                ),
                                events = MyEmojiCircleEvents(
                                    onClick = {
                                        onEmojiClick(emoji)
                                    },
                                    onLongClick = {
                                        onEmojiLongClick(emoji)
                                    },
                                ),
                            )
                        }
                    }
                }
            }
            item {
                NavigationBarSpacer()
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

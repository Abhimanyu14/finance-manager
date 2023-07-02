package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_or_edit_category.component.bottomsheet

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.capitalizeWords
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Emoji
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottom_sheet.MyEmojiPickerBottomSheetUI

@Composable
internal fun AddOrEditCategorySelectEmojiBottomSheet(
    context: Context,
    emojiGroups: Map<String, List<Emoji>>,
    searchText: String,
    resetBottomSheetType: () -> Unit,
    updateEmoji: (updatedEmoji: String) -> Unit,
    updateSearchText: (updatedSearchText: String) -> Unit,
) {
    MyEmojiPickerBottomSheetUI(
        emojiGroups = emojiGroups,
        searchText = searchText,
        onEmojiClick = { emoji ->
            resetBottomSheetType()
            updateEmoji(emoji.character)
        },
        onEmojiLongClick = { emoji ->
            Toast.makeText(
                context,
                emoji.unicodeName.capitalizeWords(),
                Toast.LENGTH_SHORT,
            ).show()
        },
        updateSearchText = { updatedSearchText ->
            updateSearchText(updatedSearchText)
        }
    )
}

package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_or_edit_category.component.bottomsheet

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.makeappssimple.abhimanyu.composeemojipicker.ComposeEmojiPickerBottomSheetUI
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.capitalizeWords

@Composable
internal fun AddOrEditCategorySelectEmojiBottomSheet(
    searchText: String,
    resetBottomSheetType: () -> Unit,
    updateEmoji: (updatedEmoji: String) -> Unit,
    updateSearchText: (updatedSearchText: String) -> Unit,
) {
    val context = LocalContext.current
    ComposeEmojiPickerBottomSheetUI(
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

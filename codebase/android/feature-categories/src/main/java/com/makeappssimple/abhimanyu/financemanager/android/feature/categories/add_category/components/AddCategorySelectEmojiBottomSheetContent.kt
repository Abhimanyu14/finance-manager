package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.components

import android.content.Context
import android.widget.Toast
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.model.Emoji
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.extensions.capitalizeWords
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.toggleModalBottomSheetState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.bottom_sheet.MyEmojiPickerBottomSheet
import kotlinx.coroutines.CoroutineScope

@Composable
internal fun AddCategorySelectEmojiBottomSheetContent(
    context: Context,
    coroutineScope: CoroutineScope,
    modalBottomSheetState: ModalBottomSheetState,
    emojis: List<Emoji>,
    searchText: String,
    resetBottomSheetType: () -> Unit,
    updateEmoji: (updatedEmoji: String) -> Unit,
    updateSearchText: (updatedSearchText: String) -> Unit,
) {
    MyEmojiPickerBottomSheet(
        emojis = emojis,
        searchText = searchText,
        onEmojiClick = { emoji ->
            toggleModalBottomSheetState(
                coroutineScope = coroutineScope,
                modalBottomSheetState = modalBottomSheetState,
            ) {
                resetBottomSheetType()
                updateEmoji(emoji.character)
            }
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
package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_category.components

import android.content.Context
import android.widget.Toast
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import com.makeappssimple.abhimanyu.financemanager.android.entities.emoji.Emoji
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.toggleModalBottomSheetState
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.bottom_sheet.EmojiPickerBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.bottom_sheet.EmojiPickerBottomSheetData
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.capitalizeWords
import kotlinx.coroutines.CoroutineScope

@Composable
fun AddCategorySelectEmojiBottomSheetContent(
    context: Context,
    coroutineScope: CoroutineScope,
    modalBottomSheetState: ModalBottomSheetState,
    emojis: List<Emoji>,
    searchText: String,
    resetBottomSheetType: () -> Unit,
    updateEmoji: (updatedEmoji: String) -> Unit,
    updateSearchText: (updatedSearchText: String) -> Unit,
) {
    EmojiPickerBottomSheet(
        data = EmojiPickerBottomSheetData(
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
        ),
    )
}

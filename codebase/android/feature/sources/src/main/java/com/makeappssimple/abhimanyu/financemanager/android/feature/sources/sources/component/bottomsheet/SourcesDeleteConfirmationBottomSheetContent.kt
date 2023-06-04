package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.component.bottomsheet

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottom_sheet.MyConfirmationBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.R

@Composable
internal fun SourcesDeleteConfirmationBottomSheetContent(
    sourceIdToDelete: Int?,
    resetBottomSheetType: () -> Unit,
    resetSourceIdToDelete: () -> Unit,
    resetExpandedItemIndex: () -> Unit,
    deleteSource: () -> Unit,
) {
    MyConfirmationBottomSheet(
        title = stringResource(
            id = R.string.screen_sources_bottom_sheet_delete_title,
        ),
        message = stringResource(
            id = R.string.screen_sources_bottom_sheet_delete_message,
        ),
        positiveButtonText = stringResource(
            id = R.string.screen_sources_bottom_sheet_delete_positive_button_text,
        ),
        negativeButtonText = stringResource(
            id = R.string.screen_sources_bottom_sheet_delete_negative_button_text,
        ),
        onPositiveButtonClick = {
            sourceIdToDelete?.let {
                deleteSource()
                resetSourceIdToDelete()
                resetExpandedItemIndex()
            }
            resetBottomSheetType()
        },
        onNegativeButtonClick = {
            resetBottomSheetType()
            resetSourceIdToDelete()
        },
    )
}

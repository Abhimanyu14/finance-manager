package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.component.bottomsheet

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottom_sheet.MyConfirmationBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.R

@Composable
internal fun SourcesSetAsDefaultConfirmationBottomSheetContent(
    clickedItemId: Int?,
    resetBottomSheetType: () -> Unit,
    resetClickedItemId: () -> Unit,
    setDefaultSourceIdInDataStore: () -> Unit,
) {
    MyConfirmationBottomSheet(
        title = stringResource(
            id = R.string.screen_sources_bottom_sheet_set_as_default_title,
        ),
        message = stringResource(
            id = R.string.screen_sources_bottom_sheet_set_as_default_message,
        ),
        positiveButtonText = stringResource(
            id = R.string.screen_sources_bottom_sheet_set_as_default_positive_button_text,
        ),
        negativeButtonText = stringResource(
            id = R.string.screen_sources_bottom_sheet_set_as_default_negative_button_text,
        ),
        onPositiveButtonClick = {
            clickedItemId?.let {
                setDefaultSourceIdInDataStore()
                resetClickedItemId()
            }
            resetBottomSheetType()
        },
        onNegativeButtonClick = {
            resetBottomSheetType()
            resetClickedItemId()
        },
    )
}

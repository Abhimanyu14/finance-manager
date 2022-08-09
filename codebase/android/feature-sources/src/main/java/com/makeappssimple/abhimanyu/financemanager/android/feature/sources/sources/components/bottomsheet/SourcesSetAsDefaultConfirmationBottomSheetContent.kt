package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.components.bottomsheet

import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.toggleModalBottomSheetState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.bottom_sheet.MyConfirmationBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.R
import kotlinx.coroutines.CoroutineScope

@Composable
internal fun SourcesSetAsDefaultConfirmationBottomSheetContent(
    coroutineScope: CoroutineScope,
    modalBottomSheetState: ModalBottomSheetState,
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
            toggleModalBottomSheetState(
                coroutineScope = coroutineScope,
                modalBottomSheetState = modalBottomSheetState,
            ) {
                clickedItemId?.let {
                    setDefaultSourceIdInDataStore()
                    resetClickedItemId()
                }
                resetBottomSheetType()
            }
        },
        onNegativeButtonClick = {
            toggleModalBottomSheetState(
                coroutineScope = coroutineScope,
                modalBottomSheetState = modalBottomSheetState,
            ) {
                resetBottomSheetType()
                resetClickedItemId()
            }
        },
    )
}

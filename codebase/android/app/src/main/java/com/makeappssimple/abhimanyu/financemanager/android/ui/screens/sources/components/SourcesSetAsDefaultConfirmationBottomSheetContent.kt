package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.sources.components

import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.R
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.toggleModalBottomSheetState
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.bottom_sheet.ConfirmationBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.bottom_sheet.ConfirmationBottomSheetData
import kotlinx.coroutines.CoroutineScope

@Composable
fun SourcesSetAsDefaultConfirmationBottomSheetContent(
    coroutineScope: CoroutineScope,
    modalBottomSheetState: ModalBottomSheetState,
    clickedItemId: Int?,
    resetBottomSheetType: () -> Unit,
    resetClickedItemId: () -> Unit,
    setDefaultSourceIdInDataStore: () -> Unit,
) {
    ConfirmationBottomSheet(
        data = ConfirmationBottomSheetData(
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
        ),
    )
}

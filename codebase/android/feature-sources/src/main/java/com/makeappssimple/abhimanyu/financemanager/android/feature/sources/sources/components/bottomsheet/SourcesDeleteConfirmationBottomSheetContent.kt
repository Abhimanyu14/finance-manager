package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.components.bottomsheet

import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.toggleModalBottomSheetState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.bottom_sheet.ConfirmationBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.bottom_sheet.ConfirmationBottomSheetData
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.R
import kotlinx.coroutines.CoroutineScope

@Composable
fun SourcesDeleteConfirmationBottomSheetContent(
    coroutineScope: CoroutineScope,
    modalBottomSheetState: ModalBottomSheetState,
    sourceIdToDelete: Int?,
    resetBottomSheetType: () -> Unit,
    resetSourceIdToDelete: () -> Unit,
    resetExpandedItemIndex: () -> Unit,
    deleteSource: () -> Unit,
) {
    ConfirmationBottomSheet(
        data = ConfirmationBottomSheetData(
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
                toggleModalBottomSheetState(
                    coroutineScope = coroutineScope,
                    modalBottomSheetState = modalBottomSheetState,
                ) {
                    sourceIdToDelete?.let {
                        deleteSource()
                        resetSourceIdToDelete()
                        resetExpandedItemIndex()
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
                    resetSourceIdToDelete()
                }
            },
        ),
    )
}

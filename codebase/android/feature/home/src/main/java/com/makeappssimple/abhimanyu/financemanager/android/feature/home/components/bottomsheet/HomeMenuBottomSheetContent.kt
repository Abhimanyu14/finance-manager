package com.makeappssimple.abhimanyu.financemanager.android.feature.home.components.bottomsheet

import androidx.annotation.StringRes
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBalance
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.toggleModalBottomSheetState
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.R
import kotlinx.coroutines.CoroutineScope

@Immutable
internal data class HomeMenuBottomSheetContentItemData(
    val iconImageVector: ImageVector,
    @StringRes val textStringResourceId: Int,
    val onClick: () -> Unit,
)

@Composable
internal fun HomeMenuBottomSheetContent(
    coroutineScope: CoroutineScope,
    modalBottomSheetState: ModalBottomSheetState,
    navigateToCategoriesScreen: () -> Unit,
    navigateToSettingsScreen: () -> Unit,
    navigateToSourcesScreen: () -> Unit,
    resetBottomSheetType: () -> Unit,
) {
    val homeMenuBottomSheetContentItemDataList = listOf(
        HomeMenuBottomSheetContentItemData(
            iconImageVector = Icons.Rounded.AccountBalance,
            textStringResourceId = R.string.screen_home_bottom_sheet_sources,
            onClick = navigateToSourcesScreen,
        ),
        HomeMenuBottomSheetContentItemData(
            iconImageVector = Icons.Rounded.Category,
            textStringResourceId = R.string.screen_home_bottom_sheet_categories,
            onClick = navigateToCategoriesScreen,
        ),
        HomeMenuBottomSheetContentItemData(
            iconImageVector = Icons.Rounded.Settings,
            textStringResourceId = R.string.screen_home_bottom_sheet_settings,
            onClick = navigateToSettingsScreen,
        ),
    )

    HomeMenuBottomSheet(
        items = homeMenuBottomSheetContentItemDataList.map { homeMenuBottomSheetContentItemData ->
            HomeMenuBottomSheetItemData(
                iconImageVector = homeMenuBottomSheetContentItemData.iconImageVector,
                text = stringResource(
                    id = homeMenuBottomSheetContentItemData.textStringResourceId,
                ),
                onClick = {
                    toggleModalBottomSheetState(
                        coroutineScope = coroutineScope,
                        modalBottomSheetState = modalBottomSheetState,
                    ) {
                        resetBottomSheetType()
                        homeMenuBottomSheetContentItemData.onClick()
                    }
                },
            )
        }
    )
}

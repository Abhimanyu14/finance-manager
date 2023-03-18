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
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.util.navigateToCategoriesScreen
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.util.navigateToSettingsScreen
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.util.navigateToSourcesScreen
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
    navigationManager: NavigationManager,
    resetBottomSheetType: () -> Unit,
) {
    val homeMenuBottomSheetContentItemDataList = listOf(
        HomeMenuBottomSheetContentItemData(
            iconImageVector = Icons.Rounded.AccountBalance,
            textStringResourceId = R.string.screen_home_bottom_sheet_sources,
            onClick = {
                navigateToSourcesScreen(
                    navigationManager = navigationManager,
                )
            },
        ),
        HomeMenuBottomSheetContentItemData(
            iconImageVector = Icons.Rounded.Category,
            textStringResourceId = R.string.screen_home_bottom_sheet_categories,
            onClick = {
                navigateToCategoriesScreen(
                    navigationManager = navigationManager,
                )
            },
        ),
        HomeMenuBottomSheetContentItemData(
            iconImageVector = Icons.Rounded.Settings,
            textStringResourceId = R.string.screen_home_bottom_sheet_settings,
            onClick = {
                navigateToSettingsScreen(
                    navigationManager = navigationManager,
                )
            },
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

/*
// TODO-Abhi: Deeplink based navigation
private fun navigateToCategoriesScreen(
    context: Context,
) {
    val intent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse("$DEEPLINK_BASE_URL/${Screen.Categories.route}"),
    )
    context.startActivity(intent)
}

private fun navigateToSettingsScreen(
    context: Context,
) {
    val intent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse("$DEEPLINK_BASE_URL/${Screen.Settings.route}"),
    )
    context.startActivity(intent)
}

private fun navigateToSourcesScreen(
    context: Context,
) {
    val intent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse("$DEEPLINK_BASE_URL/${Screen.Sources.route}"),
    )
    context.startActivity(intent)
}
*/

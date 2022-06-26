package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.home.components.bottomsheet

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.R
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateToCategoriesScreen
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateToSettingsScreen
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateToSourcesScreen
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.toggleModalBottomSheetState
import kotlinx.coroutines.CoroutineScope

@OptIn(
    ExperimentalMaterialApi::class,
)
@Composable
fun HomeMenuBottomSheetContent(
    coroutineScope: CoroutineScope,
    modalBottomSheetState: ModalBottomSheetState,
    navigationManager: NavigationManager,
    resetBottomSheetType: () -> Unit,
) {
    HomeMenuBottomSheet(
        data = HomeMenuBottomSheetData(
            items = listOf(
                HomeBottomSheetItemData(
                    text = stringResource(
                        id = R.string.screen_home_bottom_sheet_sources,
                    ),
                    onClick = {
                        toggleModalBottomSheetState(
                            coroutineScope = coroutineScope,
                            modalBottomSheetState = modalBottomSheetState,
                        ) {
                            resetBottomSheetType()
                            navigateToSourcesScreen(
                                navigationManager = navigationManager,
                            )
                        }
                    },
                ),
                HomeBottomSheetItemData(
                    text = stringResource(
                        id = R.string.screen_home_bottom_sheet_categories,
                    ),
                    onClick = {
                        toggleModalBottomSheetState(
                            coroutineScope = coroutineScope,
                            modalBottomSheetState = modalBottomSheetState,
                        ) {
                            resetBottomSheetType()
                            navigateToCategoriesScreen(
                                navigationManager = navigationManager,
                            )
                        }
                    },
                ),
                HomeBottomSheetItemData(
                    text = stringResource(
                        id = R.string.screen_home_bottom_sheet_settings,
                    ),
                    onClick = {
                        toggleModalBottomSheetState(
                            coroutineScope = coroutineScope,
                            modalBottomSheetState = modalBottomSheetState,
                        ) {
                            resetBottomSheetType()
                            navigateToSettingsScreen(
                                navigationManager = navigationManager,
                            )
                        }
                    },
                ),
            ),
        ),
    )
}

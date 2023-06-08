package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_or_edit_source.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.Logger
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_or_edit_source.screen.AddOrEditSourceScreenUIData
import kotlinx.coroutines.flow.StateFlow

interface AddOrEditSourceScreenViewModel : ScreenViewModel {
    val logger: Logger
    val navigationManager: NavigationManager
    val screenUIData: StateFlow<AddOrEditSourceScreenUIData?>

    fun insertSource()

    fun clearName()

    fun updateName(
        updatedName: TextFieldValue,
    )

    fun clearBalanceAmountValue()

    fun updateBalanceAmountValue(
        updatedBalanceAmountValue: TextFieldValue,
    )

    fun updateSelectedSourceTypeIndex(
        updatedIndex: Int,
    )

    fun updateSource()
}

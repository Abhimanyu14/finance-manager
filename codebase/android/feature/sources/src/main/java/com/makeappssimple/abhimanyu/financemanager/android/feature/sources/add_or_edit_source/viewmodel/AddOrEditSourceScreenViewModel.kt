package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_or_edit_source.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.MyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_or_edit_source.screen.AddOrEditSourceScreenUIData
import kotlinx.coroutines.flow.StateFlow

interface AddOrEditSourceScreenViewModel : ScreenViewModel {
    val myLogger: MyLogger
    val navigationManager: NavigationManager
    val screenUIData: StateFlow<MyResult<AddOrEditSourceScreenUIData>?>

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

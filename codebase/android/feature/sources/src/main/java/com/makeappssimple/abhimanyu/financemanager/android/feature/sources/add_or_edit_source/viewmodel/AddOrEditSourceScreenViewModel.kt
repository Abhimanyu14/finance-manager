package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_or_edit_source.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.Logger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.model.SourceType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface AddOrEditSourceScreenViewModel : ScreenViewModel {
    val logger: Logger
    val navigationManager: NavigationManager
    val source: Flow<Source?>
    val sourceTypes: List<SourceType>
    val selectedSourceTypeIndex: StateFlow<Int>
    val name: StateFlow<TextFieldValue>
    val balanceAmountValue: StateFlow<TextFieldValue>

    fun insertSource()

    fun isValidSourceData(): Boolean

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

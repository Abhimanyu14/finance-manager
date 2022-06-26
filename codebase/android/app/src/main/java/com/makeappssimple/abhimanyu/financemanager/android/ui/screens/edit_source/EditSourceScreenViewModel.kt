package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.edit_source

import com.makeappssimple.abhimanyu.financemanager.android.entities.source.Source
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.SourceType
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.ui.base.ScreenViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface EditSourceScreenViewModel : ScreenViewModel {
    val navigationManager: NavigationManager
    val source: Flow<Source?>
    val sourceTypes: List<SourceType>
    val selectedSourceTypeIndex: StateFlow<Int>
    val name: StateFlow<String>
    val balanceAmountValue: StateFlow<String>

    fun insertSource()

    fun isValidSourceData(): Boolean

    fun clearName()

    fun updateName(
        updatedName: String,
    )

    fun clearBalanceAmountValue()

    fun updateBalanceAmountValue(
        updatedBalanceAmountValue: String,
    )

    fun updateSelectedSourceTypeIndex(
        updatedIndex: Int,
    )

    fun deleteSource(
        id: Int,
    )

    fun updateSource()
}

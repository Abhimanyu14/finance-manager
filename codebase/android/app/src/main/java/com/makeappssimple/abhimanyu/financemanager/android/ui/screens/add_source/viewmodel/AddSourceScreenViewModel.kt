package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_source.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.entities.source.SourceType
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.ui.base.ScreenViewModel
import kotlinx.coroutines.flow.StateFlow

interface AddSourceScreenViewModel : ScreenViewModel {
    val navigationManager: NavigationManager
    val sourceTypes: List<SourceType>
    val selectedSourceTypeIndex: StateFlow<Int>
    val name: StateFlow<String>

    fun insertSource()

    fun isValidSourceData(): Boolean

    fun clearName()

    fun updateName(
        updatedName: String,
    )

    fun updateSelectedSourceTypeIndex(
        updatedIndex: Int,
    )
}

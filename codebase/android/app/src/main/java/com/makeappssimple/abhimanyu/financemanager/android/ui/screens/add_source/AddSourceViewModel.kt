package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_source

import com.makeappssimple.abhimanyu.financemanager.android.entities.source.SourceType
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.StateFlow

interface AddSourceViewModel : BaseViewModel {
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

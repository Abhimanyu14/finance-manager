package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_source.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.SourceType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
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

package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_source

import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.data.source.repository.SourceRepository
import com.makeappssimple.abhimanyu.financemanager.android.models.Amount
import com.makeappssimple.abhimanyu.financemanager.android.models.Source
import com.makeappssimple.abhimanyu.financemanager.android.models.SourceType
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateUp
import com.makeappssimple.abhimanyu.financemanager.android.ui.base.BaseViewModel
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.isNotNullOrBlank
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddSourceViewModel @Inject constructor(
    val navigationManager: NavigationManager,
    private val sourceRepository: SourceRepository,
) : BaseViewModel() {
    val sourceTypes = SourceType.values()
        .filter {
            it != SourceType.CASH
        }
    private val _selectedSourceTypeIndex = MutableStateFlow(
        value = sourceTypes.indexOf(
            element = SourceType.BANK,
        ),
    )
    val selectedSourceTypeIndex: StateFlow<Int> = _selectedSourceTypeIndex
    private val _name = MutableStateFlow(
        value = "",
    )
    val name: StateFlow<String> = _name

    override fun trackScreen() {
        // TODO-Abhi: Add screen tracking code
    }

    fun insertSource() {
        viewModelScope.launch(
            context = Dispatchers.IO,
        ) {
            sourceRepository.insertSource(
                source = Source(
                    balanceAmount = Amount(
                        value = 0L,
                    ),
                    type = sourceTypes[selectedSourceTypeIndex.value],
                    name = name.value,
                ),
            )
            navigateUp(
                navigationManager = navigationManager,
            )
        }
    }

    fun isValidSourceData(): Boolean {
        return name.value.isNotNullOrBlank()
    }

    fun clearName() {
        _name.value = ""
    }

    fun updateName(
        updatedName: String,
    ) {
        _name.value = updatedName
    }

    fun updateSelectedSourceTypeIndex(
        updatedIndex: Int,
    ) {
        _selectedSourceTypeIndex.value = updatedIndex
    }
}

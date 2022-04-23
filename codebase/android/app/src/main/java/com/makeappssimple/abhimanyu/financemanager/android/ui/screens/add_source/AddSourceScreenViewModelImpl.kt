package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_source

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.InsertSourceUseCase
import com.makeappssimple.abhimanyu.financemanager.android.entities.amount.Amount
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.Source
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.SourceType
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateUp
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.isNotNullOrBlank
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddSourceScreenViewModelImpl @Inject constructor(
    override val navigationManager: NavigationManager,
    private val dispatcherProvider: DispatcherProvider,
    private val insertSourceUseCase: InsertSourceUseCase,
) : AddSourceScreenViewModel, ViewModel() {
    override val sourceTypes: List<SourceType> = SourceType.values()
        .filter {
            it != SourceType.CASH
        }

    private val _selectedSourceTypeIndex = MutableStateFlow(
        value = sourceTypes.indexOf(
            element = SourceType.BANK,
        ),
    )
    override val selectedSourceTypeIndex: StateFlow<Int> = _selectedSourceTypeIndex

    private val _name = MutableStateFlow(
        value = "",
    )
    override val name: StateFlow<String> = _name

    override fun trackScreen() {
        // TODO-Abhi: Add screen tracking code
    }

    override fun insertSource() {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            insertSourceUseCase(
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

    override fun isValidSourceData(): Boolean {
        return name.value.isNotNullOrBlank()
    }

    override fun clearName() {
        _name.value = ""
    }

    override fun updateName(
        updatedName: String,
    ) {
        _name.value = updatedName
    }

    override fun updateSelectedSourceTypeIndex(
        updatedIndex: Int,
    ) {
        _selectedSourceTypeIndex.value = updatedIndex
    }
}

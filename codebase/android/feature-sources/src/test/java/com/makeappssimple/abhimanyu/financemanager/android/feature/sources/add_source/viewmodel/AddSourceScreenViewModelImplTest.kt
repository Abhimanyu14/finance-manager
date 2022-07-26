package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_source.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.database.amount.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.SourceType
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.InsertSourceUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.testing.TestDispatcherProviderImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class AddSourceScreenViewModelImplTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val navigationManager: NavigationManager = mock()
    private val dispatcherProvider: DispatcherProvider = TestDispatcherProviderImpl(
        testDispatcher = mainDispatcherRule.testDispatcher,
    )
    private val insertSourceUseCase: InsertSourceUseCase = mock()

    private lateinit var addSourcesScreenViewModel: AddSourceScreenViewModel

    @Before
    fun setUp() {
        addSourcesScreenViewModel =
            AddSourceScreenViewModelImpl(
                navigationManager = navigationManager,
                dispatcherProvider = dispatcherProvider,
                insertSourceUseCase = insertSourceUseCase,
            )
    }

    @Test
    fun getSourceTypes() {
        Assert.assertEquals(
            listOf(
                SourceType.BANK,
                SourceType.E_WALLET,
            ),
            addSourcesScreenViewModel.sourceTypes,
        )
    }

    @Test
    fun getSelectedSourceTypeIndex() = runTest {
        Assert.assertEquals(
            0,
            addSourcesScreenViewModel.selectedSourceTypeIndex.first(),
        )
    }

    @Test
    fun getName() = runTest {
        Assert.assertEquals(
            "",
            addSourcesScreenViewModel.name.first(),
        )
    }

    @Test
    fun insertSource() = runTest {
        val balanceAmount = Amount(
            value = 0L,
        )
        val source = Source(
            balanceAmount = balanceAmount,
            type = SourceType.BANK,
            name = "",
        )

        addSourcesScreenViewModel.insertSource()

        verify(
            mock = insertSourceUseCase,
        ).invoke(
            source = source,
        )
    }

    @Test
    fun isValidSourceData_nameIsBlank_returnsFalse() {
        Assert.assertFalse(addSourcesScreenViewModel.isValidSourceData())
    }

    @Test
    fun isValidSourceData_nameIsCash_returnsFalse() {
        val testString = "cash"

        addSourcesScreenViewModel.updateName(
            updatedName = testString,
        )

        Assert.assertFalse(addSourcesScreenViewModel.isValidSourceData())
    }

    @Test
    fun isValidSourceData_nameIsNotCash_returnsTrue() {
        val testString = "testString"
        addSourcesScreenViewModel.updateName(
            updatedName = testString,
        )

        Assert.assertTrue(addSourcesScreenViewModel.isValidSourceData())
    }

    @Test
    fun updateName() = runTest {
        val testString = "testString"

        addSourcesScreenViewModel.updateName(
            updatedName = testString,
        )

        Assert.assertEquals(
            testString,
            addSourcesScreenViewModel.name.first(),
        )
    }

    @Test
    fun clearName() = runTest {
        val testString = "testString"
        addSourcesScreenViewModel.updateName(
            updatedName = testString,
        )

        addSourcesScreenViewModel.clearName()

        Assert.assertEquals(
            "",
            addSourcesScreenViewModel.name.first(),
        )
    }

    @Test
    fun updateSelectedSourceTypeIndex() = runTest {
        val testIndex = 1

        addSourcesScreenViewModel.updateSelectedSourceTypeIndex(
            updatedIndex = testIndex,
        )

        Assert.assertEquals(
            testIndex,
            addSourcesScreenViewModel.selectedSourceTypeIndex.first(),
        )
    }
}

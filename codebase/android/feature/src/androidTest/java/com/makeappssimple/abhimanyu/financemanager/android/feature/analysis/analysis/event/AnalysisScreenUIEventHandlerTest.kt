package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.analysis.event

import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.analysis.Filter
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.analysis.bottomsheet.AnalysisScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.analysis.state.AnalysisScreenUIStateEvents
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class AnalysisScreenUIEventHandlerTest {
    private val uiStateEvents: AnalysisScreenUIStateEvents = mock()
    private lateinit var analysisScreenUIEventHandler: AnalysisScreenUIEventHandler

    @Before
    fun setUp() {
        analysisScreenUIEventHandler = AnalysisScreenUIEventHandler(
            uiStateEvents = uiStateEvents,
        )
    }

    @Test
    fun `on filter action button click`() {
        val setScreenBottomSheetType: (AnalysisScreenBottomSheetType) -> Unit = mock()
        whenever(
            methodCall = uiStateEvents.updateScreenBottomSheetType,
        ).thenReturn(setScreenBottomSheetType)

        analysisScreenUIEventHandler.handleUIEvent(AnalysisScreenUIEvent.OnFilterActionButtonClick)

        verify(
            mock = setScreenBottomSheetType,
        )(AnalysisScreenBottomSheetType.Filters)
    }

    @Test
    fun `on navigation back button click`() {
        val resetScreenBottomSheetType: () -> Unit = mock()
        whenever(
            methodCall = uiStateEvents.resetScreenBottomSheetType,
        ).thenReturn(resetScreenBottomSheetType)

        analysisScreenUIEventHandler.handleUIEvent(AnalysisScreenUIEvent.OnNavigationBackButtonClick)

        verify(
            mock = resetScreenBottomSheetType,
        ).invoke()
    }

    @Test
    fun `on top app bar navigation button click`() {
        val navigateUp: () -> Unit = mock()
        whenever(
            methodCall = uiStateEvents.navigateUp,
        ).thenReturn(navigateUp)

        analysisScreenUIEventHandler.handleUIEvent(AnalysisScreenUIEvent.OnTopAppBarNavigationButtonClick)

        verify(
            mock = navigateUp,
        ).invoke()
    }

    @Test
    fun `on analysis filter bottom sheet positive button click`() {
        val updatedSelectedFilter = Filter()
        val resetScreenBottomSheetType: () -> Unit = mock()
        val setSelectedFilter: (Filter) -> Unit = mock()
        whenever(
            methodCall = uiStateEvents.updateSelectedFilter,
        ).thenReturn(setSelectedFilter)
        whenever(
            methodCall = uiStateEvents.resetScreenBottomSheetType,
        ).thenReturn(resetScreenBottomSheetType)

        analysisScreenUIEventHandler.handleUIEvent(
            AnalysisScreenUIEvent.OnAnalysisFilterBottomSheet.PositiveButtonClick(
                updatedSelectedFilter = updatedSelectedFilter,
            )
        )

        verify(
            mock = setSelectedFilter,
        ).invoke(updatedSelectedFilter)
        verify(
            mock = resetScreenBottomSheetType,
        ).invoke()
    }

    @Test
    fun `on transaction type change`() {
        val updatedSelectedTransactionTypeIndex = 1
        val setSelectedTransactionTypeIndex: (Int) -> Unit = mock()
        whenever(
            methodCall = uiStateEvents.updateSelectedTransactionTypeIndex,
        ).thenReturn(setSelectedTransactionTypeIndex)

        analysisScreenUIEventHandler.handleUIEvent(
            AnalysisScreenUIEvent.OnTransactionTypeChange(
                updatedSelectedTransactionTypeIndex = updatedSelectedTransactionTypeIndex,
            )
        )

        verify(
            mock = setSelectedTransactionTypeIndex,
        ).invoke(updatedSelectedTransactionTypeIndex)
    }
}

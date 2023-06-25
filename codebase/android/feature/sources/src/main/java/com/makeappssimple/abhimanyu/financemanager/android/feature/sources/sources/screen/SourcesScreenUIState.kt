package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.component.listitem.SourcesListItemData

@Stable
class SourcesScreenUIState(
    data: MyResult<SourcesScreenUIData>?,
    val clickedItemId: Int?,
    val expandedItemIndex: Int?,
    val sourceIdToDelete: Int?,
    val sourcesBottomSheetType: SourcesBottomSheetType,
    val setClickedItemId: (Int?) -> Unit,
    val setExpandedItemIndex: (Int?) -> Unit,
    val setSourceIdToDelete: (Int?) -> Unit,
    val setSourcesBottomSheetType: (SourcesBottomSheetType) -> Unit,
) {
    private val unwrappedData = when (data) {
        is MyResult.Success -> {
            data.data
        }

        else -> {
            null
        }
    }

    val isLoading: Boolean = unwrappedData.isNull()
    val sourcesListItemDataList: List<SourcesListItemData> =
        unwrappedData?.sourcesListItemDataList.orEmpty()
    val resetBottomSheetType: () -> Unit = {
        setSourcesBottomSheetType(SourcesBottomSheetType.NONE)
    }
}

@Composable
fun rememberSourcesScreenUIState(
    data: MyResult<SourcesScreenUIData>?,
): SourcesScreenUIState {
    var clickedItemId: Int? by remember {
        mutableStateOf(
            value = null,
        )
    }
    var expandedItemIndex: Int? by remember {
        mutableStateOf(
            value = null,
        )
    }
    var sourceIdToDelete: Int? by remember {
        mutableStateOf(
            value = null,
        )
    }
    val (sourcesBottomSheetType: SourcesBottomSheetType, setSourcesBottomSheetType: (SourcesBottomSheetType) -> Unit) = remember {
        mutableStateOf(
            value = SourcesBottomSheetType.NONE,
        )
    }
    val setClickedItemId = { updatedClickedItemId: Int? ->
        clickedItemId = updatedClickedItemId
    }
    val setExpandedItemIndex = { updatedExpandedItemIndex: Int? ->
        expandedItemIndex = updatedExpandedItemIndex
    }
    val setSourceIdToDelete = { updatedSourceIdToDelete: Int? ->
        sourceIdToDelete = updatedSourceIdToDelete
    }

    return remember(
        data,
        clickedItemId,
        expandedItemIndex,
        sourceIdToDelete,
        sourcesBottomSheetType,
        setClickedItemId,
        setExpandedItemIndex,
        setSourceIdToDelete,
        setSourcesBottomSheetType,
    ) {
        SourcesScreenUIState(
            data = data,
            sourcesBottomSheetType = sourcesBottomSheetType,
            expandedItemIndex = expandedItemIndex,
            clickedItemId = clickedItemId,
            sourceIdToDelete = sourceIdToDelete,
            setSourcesBottomSheetType = setSourcesBottomSheetType,
            setExpandedItemIndex = setExpandedItemIndex,
            setClickedItemId = setClickedItemId,
            setSourceIdToDelete = setSourceIdToDelete,
        )
    }
}

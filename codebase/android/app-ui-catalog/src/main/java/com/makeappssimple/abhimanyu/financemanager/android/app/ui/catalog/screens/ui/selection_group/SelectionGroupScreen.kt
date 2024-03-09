package com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.screens.ui.selection_group

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.R
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.addIfDoesNotContainItemElseRemove
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MyHorizontalScrollingRadioGroup
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MyHorizontalScrollingRadioGroupData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MyHorizontalScrollingRadioGroupEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MyHorizontalScrollingSelectionGroup
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MyHorizontalScrollingSelectionGroupData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MyHorizontalScrollingSelectionGroupEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MyRadioGroup
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MyRadioGroupData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MyRadioGroupEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MySelectionGroup
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MySelectionGroupData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group.MySelectionGroupEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.top_app_bar.MyTopAppBar

@Composable
fun SelectionGroupScreen(
    navigateUp: () -> Unit,
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    MyScaffold(
        sheetContent = {},
        onClick = { },
        coroutineScope = coroutineScope,
        onBackPress = { },
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.screen_selection_group,
                navigationAction = navigateUp,
            )
        },
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement
                .spacedBy(
                    space = 8.dp,
                ),
        ) {
            val selectionGroupItems = listOf(
                ChipUIData(
                    text = "Item 1",
                ),
                ChipUIData(
                    text = "Item 2",
                ),
                ChipUIData(
                    text = "Item 3",
                ),
                ChipUIData(
                    text = "Item 4",
                ),
                ChipUIData(
                    text = "Item 5",
                ),
                ChipUIData(
                    text = "Item 6",
                ),
            )
            val selectedSelectedGroupItemsIndices = remember {
                mutableStateListOf<Int>()
            }
            MySelectionGroup(
                data = MySelectionGroupData(
                    items = selectionGroupItems,
                    selectedItemsIndices = selectedSelectedGroupItemsIndices,
                ),
                events = MySelectionGroupEvents(
                    onSelectionChange = {
                        selectedSelectedGroupItemsIndices.addIfDoesNotContainItemElseRemove(it)
                    },
                ),
            )

            val radioGroupItems = listOf(
                ChipUIData(
                    text = "Item 1",
                ),
                ChipUIData(
                    text = "Item 2",
                ),
                ChipUIData(
                    text = "Item 3",
                ),
                ChipUIData(
                    text = "Item 4",
                ),
                ChipUIData(
                    text = "Item 5",
                ),
                ChipUIData(
                    text = "Item 6",
                ),
            )
            var selectedRadioGroupItemIndex by remember {
                mutableIntStateOf(0)
            }
            MyRadioGroup(
                data = MyRadioGroupData(
                    items = radioGroupItems,
                    selectedItemIndex = selectedRadioGroupItemIndex,
                ),
                events = MyRadioGroupEvents(
                    onSelectionChange = {
                        selectedRadioGroupItemIndex = it
                    },
                ),
            )

            val horizontalScrollingRadioGroupItems = listOf(
                ChipUIData(
                    text = "Item 1",
                ),
                ChipUIData(
                    text = "Item 2",
                ),
                ChipUIData(
                    text = "Item 3",
                ),
                ChipUIData(
                    text = "Item 4",
                ),
                ChipUIData(
                    text = "Item 5",
                ),
                ChipUIData(
                    text = "Item 6",
                ),
            )
            var selectedHorizontalScrollingRadioGroupItemIndex by remember {
                mutableIntStateOf(0)
            }
            MyHorizontalScrollingRadioGroup(
                data = MyHorizontalScrollingRadioGroupData(
                    items = horizontalScrollingRadioGroupItems,
                    selectedItemIndex = selectedHorizontalScrollingRadioGroupItemIndex,
                ),
                events = MyHorizontalScrollingRadioGroupEvents(
                    onSelectionChange = {
                        selectedHorizontalScrollingRadioGroupItemIndex = it
                    },
                ),
            )

            val horizontalScrollingSelectionGroupItems = listOf(
                ChipUIData(
                    text = "Item 1",
                ),
                ChipUIData(
                    text = "Item 2",
                ),
                ChipUIData(
                    text = "Item 3",
                ),
                ChipUIData(
                    text = "Item 4",
                ),
                ChipUIData(
                    text = "Item 5",
                ),
                ChipUIData(
                    text = "Item 6",
                ),
            )
            val selectedHorizontalScrollingSelectedGroupItemsIndices = remember {
                mutableStateListOf<Int>()
            }
            MyHorizontalScrollingSelectionGroup(
                data = MyHorizontalScrollingSelectionGroupData(
                    items = horizontalScrollingSelectionGroupItems,
                ),
                events = MyHorizontalScrollingSelectionGroupEvents(
                    onSelectionChange = {
                        Toast.makeText(
                            context,
                            "Selected ${horizontalScrollingSelectionGroupItems[it].text}",
                            Toast.LENGTH_SHORT,
                        ).show()
                    },
                ),
            )
        }
    }
}

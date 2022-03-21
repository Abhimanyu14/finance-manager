package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.sources

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.makeappssimple.abhimanyu.financemanager.android.R
import com.makeappssimple.abhimanyu.financemanager.android.models.sortOrder
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateToAddSourceScreen
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MyFloatingActionButton
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.ScaffoldContentWrapper
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Primary

data class SourcesScreenViewData(
    val screenViewModel: SourcesViewModel,
)

@ExperimentalMaterialApi
@Composable
fun SourcesScreenView(
    data: SourcesScreenViewData,
) {
    val focusManager = LocalFocusManager.current
    val scaffoldState = rememberScaffoldState()

    val sources by data.screenViewModel.sources.collectAsState(
        initial = emptyList(),
    )
    var total by remember {
        mutableStateOf(
            value = "",
        )
    }

    LaunchedEffect(
        key1 = sources,
    ) {
        total = sources.sumOf {
            it.balanceAmount.value
        }.toString()
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            MyTopAppBar(
                navigationManager = data.screenViewModel.navigationManager,
                titleText = stringResource(
                    id = R.string.screen_sources_appbar_title,
                ),
                isNavigationIconVisible = true,
            )
        },
        floatingActionButton = {
            MyFloatingActionButton(
                contentDescription = stringResource(
                    id = R.string.screen_sources_floating_action_button_content_description,
                ),
                onClick = {
                    navigateToAddSourceScreen(
                        navigationManager = data.screenViewModel.navigationManager,
                    )
                },
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        isFloatingActionButtonDocked = true,
        modifier = Modifier
            .fillMaxSize(),
    ) { innerPadding ->
        ScaffoldContentWrapper(
            innerPadding = innerPadding,
            onClick = {
                focusManager.clearFocus()
            },
        ) {
            LazyColumn {
                item {
                    Text(
                        text = "TOTAL : $total",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            color = Primary,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 16.dp,
                                vertical = 8.dp,
                            ),
                    )
                }
                itemsIndexed(
                    items = sources
                        .sortedWith(
                            comparator = compareBy {
                                it.type.sortOrder
                            }
                        ),
                    key = { _, listItem ->
                        listItem.hashCode()
                    },
                ) { _, listItem ->
                    SourceListItem(
                        source = listItem,
                        swipeToDeleteEnabled = !listItem.name.contains(
                            other = "Cash",
                            ignoreCase = false,
                        ),
                        deleteSource = {
                            data.screenViewModel.deleteSource(
                                id = listItem.id,
                            )
                        },
                    )
                }
            }
        }
    }
}

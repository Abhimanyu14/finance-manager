package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.ContentAlpha
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.MyAppTheme
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.PrimaryContainer
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Surface

@ExperimentalMaterial3Api
@Composable
fun HomeScreenView() {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row {
                        Text(
                            text = "Finance Manager",
                        )
                    }
                },
                modifier = Modifier
                    .background(Surface)
                    .statusBarsPadding(),
            )
        },
        bottomBar = {
            Column {
                BottomAppBar(
                    backgroundColor = PrimaryContainer,
                    cutoutShape = CircleShape,
                ) {
                    // Leading icons should typically have a high content alpha
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
                        IconButton(
                            onClick = {

                            },
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Menu",
                            )
                        }
                    }
                    Spacer(
                        modifier = Modifier
                            .weight(
                                weight = 1f,
                            ),
                    )
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(PrimaryContainer)
                        .navigationBarsPadding(),
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                backgroundColor = PrimaryContainer,
                onClick = {
                },
            ) {
                IconButton(
                    onClick = {
                    },
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add",
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        isFloatingActionButtonDocked = true,
        modifier = Modifier
            .fillMaxSize(),
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .background(Surface)
                .fillMaxSize()
                .navigationBarsPadding()
                .padding(
                    paddingValues = innerPadding,
                ),
        ) {

        }
    }
}

@ExperimentalMaterial3Api
@Preview
@Composable
private fun HomeScreenViewPreview() {
    MyAppTheme {
        HomeScreenView()
    }
}

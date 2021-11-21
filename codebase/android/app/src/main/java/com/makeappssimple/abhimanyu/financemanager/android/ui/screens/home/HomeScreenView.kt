package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.MyAppTheme

@Composable
fun HomeScreenView() {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize(),
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(
                    paddingValues = innerPadding,
                )
                .fillMaxSize(),
        ) {

        }
    }
}

@Preview
@Composable
private fun HomeScreenViewPreview() {
    MyAppTheme {
        HomeScreenView()
    }
}

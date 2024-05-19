package com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.screen.ui.save_button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.R
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.save_button.SaveButton
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.save_button.SaveButtonData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.top_app_bar.MyTopAppBar

@Composable
public fun CatalogSaveButtonScreen(
    navigateUp: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    MyScaffold(
        sheetContent = {},
        onClick = { },
        coroutineScope = coroutineScope,
        onNavigationBackButtonClick = { },
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.screen_save_button,
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
            SaveButton(
                modifier = Modifier,
                data = SaveButtonData(
                    isEnabled = false,
                    textStringResourceId = R.string.screen_save_button_label,
                ),
            )
            SaveButton(
                modifier = Modifier,
                data = SaveButtonData(
                    isEnabled = true,
                    textStringResourceId = R.string.screen_save_button_label,
                ),
            )
            SaveButton(
                modifier = Modifier,
                data = SaveButtonData(
                    isEnabled = true,
                    isLoading = true,
                    textStringResourceId = R.string.screen_save_button_label,
                ),
            )
        }
    }
}

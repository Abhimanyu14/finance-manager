package com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.screens.foundation.text

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.R
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.top_app_bar.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold

@Composable
fun TextScreen(
    navigateUp: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    MyScaffold(
        sheetContent = {},
        onClick = { },
        coroutineScope = coroutineScope,
        onBackPress = { },
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.screen_text,
                navigationAction = navigateUp,
            )
        },
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 16.dp,
                ),
            verticalArrangement = Arrangement
                .spacedBy(
                    space = 16.dp,
                ),
        ) {
            MyText(
                text = "Display Large",
                style = MaterialTheme.typography.displayLarge,
            )
            MyText(
                text = "Display Medium",
                style = MaterialTheme.typography.displayMedium,
            )
            MyText(
                text = "Display Small",
                style = MaterialTheme.typography.displaySmall,
            )
            MyText(
                text = "Headline Large",
                style = MaterialTheme.typography.headlineLarge,
            )
            MyText(
                text = "Headline Medium",
                style = MaterialTheme.typography.headlineMedium,
            )
            MyText(
                text = "Headline Small",
                style = MaterialTheme.typography.headlineSmall,
            )
            MyText(
                text = "Title Large",
                style = MaterialTheme.typography.titleLarge,
            )
            MyText(
                text = "Title Medium",
                style = MaterialTheme.typography.titleMedium,
            )
            MyText(
                text = "Title Small",
                style = MaterialTheme.typography.titleSmall,
            )
            MyText(
                text = "Body Large",
                style = MaterialTheme.typography.bodyLarge,
            )
            MyText(
                text = "Body Medium",
                style = MaterialTheme.typography.bodyMedium,
            )
            MyText(
                text = "Body Small",
                style = MaterialTheme.typography.bodySmall,
            )
            MyText(
                text = "Label Large",
                style = MaterialTheme.typography.labelLarge,
            )
            MyText(
                text = "Label Medium",
                style = MaterialTheme.typography.labelMedium,
            )
            MyText(
                text = "Label Small",
                style = MaterialTheme.typography.labelSmall,
            )
        }
    }
}

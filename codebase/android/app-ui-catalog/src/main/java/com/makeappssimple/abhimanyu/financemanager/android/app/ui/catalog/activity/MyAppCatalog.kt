package com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.activity

import androidx.compose.runtime.Composable
import com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.navigation.MyNavGraph
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.MyAppTheme

@Composable
fun MyAppCatalog() {
    MyAppTheme {
        MyNavGraph()
    }
}

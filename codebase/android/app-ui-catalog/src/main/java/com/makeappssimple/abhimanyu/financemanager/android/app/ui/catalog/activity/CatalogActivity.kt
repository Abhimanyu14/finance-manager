package com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.activity

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

public class CatalogActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setDarkStatusBarIcons()

        setContent {
            CatalogApp()
        }
    }
}

public fun Activity.setDarkStatusBarIcons() {
    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
}

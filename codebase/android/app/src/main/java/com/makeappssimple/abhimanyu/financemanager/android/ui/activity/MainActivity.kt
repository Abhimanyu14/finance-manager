package com.makeappssimple.abhimanyu.financemanager.android.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.MyAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?,
    ) {
        super.onCreate(savedInstanceState)

        setContent {
            MyAppTheme {
                MyApp()
            }
        }
    }
}

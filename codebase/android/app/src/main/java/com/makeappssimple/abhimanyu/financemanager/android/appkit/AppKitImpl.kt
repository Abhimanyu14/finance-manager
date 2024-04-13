package com.makeappssimple.abhimanyu.financemanager.android.appkit

import android.content.Context
import android.content.Intent
import com.makeappssimple.abhimanyu.financemanager.android.MainActivity
import com.makeappssimple.abhimanyu.financemanager.android.core.appkit.AppKit

public class AppKitImpl(
    private val context: Context,
) : AppKit {
    override fun getMainActivityIntent(): Intent {
        return Intent(context, MainActivity::class.java)
    }
}

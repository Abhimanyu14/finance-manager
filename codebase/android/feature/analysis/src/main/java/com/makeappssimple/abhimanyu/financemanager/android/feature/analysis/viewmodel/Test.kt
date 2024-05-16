package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.viewmodel

import android.content.Context

public class ContextOrderSample {

    //FAIL
    public fun foo(bar: String, context: Context) {

    }

    //SUCCESS
    public fun foo(context: Context, bar: String) {

    }
}


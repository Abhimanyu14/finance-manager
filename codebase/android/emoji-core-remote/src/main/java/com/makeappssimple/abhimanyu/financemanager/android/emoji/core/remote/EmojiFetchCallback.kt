package com.makeappssimple.abhimanyu.financemanager.android.emoji.core.remote

interface EmojiFetchCallback {
    fun onFetchSuccess(
        data: String,
    )

    fun onFetchFailure(
        errorMessage: String,
    )
}

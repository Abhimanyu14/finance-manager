package com.makeappssimple.abhimanyu.financemanager.android.emoji.core.emojifetcher

interface EmojiFetchCallback {
    fun onFetchSuccess(
        data: String,
    )

    fun onFetchFailure(
        errorMessage: String,
    )
}

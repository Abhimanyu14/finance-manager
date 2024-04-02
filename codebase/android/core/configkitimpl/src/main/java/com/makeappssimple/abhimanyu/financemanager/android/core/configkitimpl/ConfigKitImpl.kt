package com.makeappssimple.abhimanyu.financemanager.android.core.configkitimpl

import android.content.Context
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.remoteConfig
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.configkit.ConfigKit
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository

class ConfigKitImpl(
    private val context: Context,
    private val dispatcherProvider: DispatcherProvider,
    private val myPreferencesRepository: MyPreferencesRepository,
) : ConfigKit {
    override fun isEnabled(): Boolean {
        Firebase.remoteConfig
        return true
    }
}

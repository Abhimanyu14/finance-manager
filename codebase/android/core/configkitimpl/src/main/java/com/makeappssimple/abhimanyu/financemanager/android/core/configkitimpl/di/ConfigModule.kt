package com.makeappssimple.abhimanyu.financemanager.android.core.configkitimpl.di

import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.remoteconfig.remoteConfig
import com.makeappssimple.abhimanyu.financemanager.android.core.configkitimpl.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ConfigModule {
    @Singleton
    @Provides
    fun provideFirebaseRemoteConfigSettings(): FirebaseRemoteConfigSettings {
        /*
        // TODO(Abhi): Use this after adding dependency on debug build checks
        val remoteConfigFetchInterval: Long = if (isDebugBuild()) {
            0L
        } else {
            3600L
        }
        */
        val remoteConfigFetchInterval = 3600L
        return FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(remoteConfigFetchInterval)
            .build()
    }

    @Singleton
    @Provides
    fun provideFirebaseRemoteConfig(
        firebaseRemoteConfigSettings: FirebaseRemoteConfigSettings,
    ): FirebaseRemoteConfig {
        val firebaseRemoteConfig = Firebase.remoteConfig

        // Set firebase remote config settings
        firebaseRemoteConfig.setConfigSettingsAsync(firebaseRemoteConfigSettings)

        // Set default firebase remote config values
        firebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)

        return firebaseRemoteConfig
    }
}

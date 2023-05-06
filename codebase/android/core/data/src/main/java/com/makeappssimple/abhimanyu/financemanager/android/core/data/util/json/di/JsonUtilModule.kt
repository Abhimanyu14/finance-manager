package com.makeappssimple.abhimanyu.financemanager.android.core.data.util.json.di

import android.content.Context
import com.makeappssimple.abhimanyu.financemanager.android.core.data.util.json.JsonUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.data.util.json.JsonUtilImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class JsonUtilModule {
    @Singleton
    @Provides
    fun providesJsonUtil(
        @ApplicationContext context: Context,
    ): JsonUtil {
        return JsonUtilImpl(
            context = context,
        )
    }
}

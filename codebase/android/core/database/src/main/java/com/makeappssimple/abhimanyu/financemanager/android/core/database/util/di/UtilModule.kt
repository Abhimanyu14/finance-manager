package com.makeappssimple.abhimanyu.financemanager.android.core.database.util.di

import android.content.Context
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.JsonUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.JsonUtilImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UtilModule {
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

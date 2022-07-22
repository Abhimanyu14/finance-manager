package com.makeappssimple.abhimanyu.financemanager.android.core.database.utils.di

import android.content.Context
import com.makeappssimple.abhimanyu.financemanager.android.core.database.utils.JsonUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.database.utils.JsonUtilImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UtilsModule {

    @Provides
    fun providesJsonUtil(
        @ApplicationContext context: Context,
    ): JsonUtil {
        return JsonUtilImpl(
            context = context,
        )
    }
}

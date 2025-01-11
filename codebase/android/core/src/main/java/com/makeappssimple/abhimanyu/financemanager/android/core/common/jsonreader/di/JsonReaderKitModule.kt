package com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonreader.di

import android.content.Context
import com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonreader.JsonReaderKit
import com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonreader.JsonReaderKitImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
public class JsonReaderKitModule {
    @Provides
    public fun providesJsonReader(
        @ApplicationContext context: Context,
    ): JsonReaderKit {
        return JsonReaderKitImpl(
            context = context,
        )
    }
}

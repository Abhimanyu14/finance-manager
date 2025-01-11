package com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonwriter.di

import android.content.Context
import com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonwriter.JsonWriterKit
import com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonwriter.JsonWriterKitImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
public class JsonWriterKitModule {
    @Provides
    public fun providesJsonWriter(
        @ApplicationContext context: Context,
    ): JsonWriterKit {
        return JsonWriterKitImpl(
            context = context,
        )
    }
}

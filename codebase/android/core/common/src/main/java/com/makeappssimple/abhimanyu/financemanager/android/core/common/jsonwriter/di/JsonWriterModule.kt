package com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonwriter.di

import android.content.Context
import com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonwriter.JsonWriter
import com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonwriter.JsonWriterImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class JsonWriterModule {
    @Singleton
    @Provides
    fun providesJsonWriter(
        @ApplicationContext context: Context,
    ): JsonWriter {
        return JsonWriterImpl(
            context = context,
        )
    }
}

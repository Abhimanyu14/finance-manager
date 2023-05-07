package com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonreader.di

import android.content.Context
import com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonreader.JsonReader
import com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonreader.JsonReaderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class JsonReaderModule {
    @Singleton
    @Provides
    fun providesJsonReader(
        @ApplicationContext context: Context,
    ): JsonReader {
        return JsonReaderImpl(
            context = context,
        )
    }
}

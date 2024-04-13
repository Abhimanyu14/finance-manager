package com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonwriter.di

import android.content.Context
import com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonwriter.MyJsonWriter
import com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonwriter.MyJsonWriterImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
public class JsonWriterModule {
    @Singleton
    @Provides
    public fun providesJsonWriter(
        @ApplicationContext context: Context,
    ): MyJsonWriter {
        return MyJsonWriterImpl(
            context = context,
        )
    }
}

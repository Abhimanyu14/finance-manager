package com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonreader.di

import android.content.Context
import com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonreader.MyJsonReader
import com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonreader.MyJsonReaderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
public class JsonReaderModule {
    @Singleton
    @Provides
    public fun providesJsonReader(
        @ApplicationContext context: Context,
    ): MyJsonReader {
        return MyJsonReaderImpl(
            context = context,
        )
    }
}

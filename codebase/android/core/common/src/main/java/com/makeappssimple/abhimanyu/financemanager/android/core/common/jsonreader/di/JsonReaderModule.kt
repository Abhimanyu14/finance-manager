package com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonreader.di

import com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonreader.JsonReader
import com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonreader.JsonReaderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface JsonReaderModule {
    @Singleton
    @Binds
    fun bindJsonReader(
        jsonReaderImpl: JsonReaderImpl,
    ): JsonReader
}

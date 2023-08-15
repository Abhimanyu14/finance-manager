package com.makeappssimple.abhimanyu.financemanager.android.emoji.core.remote.di

import android.content.Context
import com.makeappssimple.abhimanyu.financemanager.android.emoji.core.remote.EmojiFetcher
import com.makeappssimple.abhimanyu.financemanager.android.emoji.core.remote.datasource.EmojiDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class EmojiDataSourceModule {
    @Singleton
    @Provides
    fun providesEmojiFetcher(
        @ApplicationContext context: Context,
    ): EmojiFetcher {
        return EmojiFetcher(
            context = context,
        )
    }

    @Singleton
    @Provides
    fun providesEmojiDataSource(
        emojiFetcher: EmojiFetcher,
    ): EmojiDataSource {
        return EmojiDataSource(
            emojiFetcher = emojiFetcher,
        )
    }
}

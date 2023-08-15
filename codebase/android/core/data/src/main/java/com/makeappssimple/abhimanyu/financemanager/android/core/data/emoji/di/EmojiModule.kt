package com.makeappssimple.abhimanyu.financemanager.android.core.data.emoji.di

import android.content.Context
import com.makeappssimple.abhimanyu.financemanager.android.core.data.emoji.repository.EmojiRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.emoji.repository.EmojiRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.emoji.usecase.GetAllEmojisUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.emoji.usecase.GetAllEmojisUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.emoji.core.datasource.EmojiDataSource
import com.makeappssimple.abhimanyu.financemanager.android.emoji.core.datasource.EmojiDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class EmojiModule {
    @Singleton
    @Provides
    fun providesEmojiRepository(
        @ApplicationContext context: Context,
    ): EmojiRepository {
        val emojiDataSource: EmojiDataSource = EmojiDataSourceImpl(
            cacheFile = File(context.cacheDir, "http_cache"),
        )
        return EmojiRepositoryImpl(
            emojiDataSource = emojiDataSource,
        )
    }

    @Singleton
    @Provides
    fun providesGetAllEmojisUseCase(
        emojiRepository: EmojiRepository,
    ): GetAllEmojisUseCase {
        return GetAllEmojisUseCaseImpl(
            emojiRepository = emojiRepository,
        )
    }
}

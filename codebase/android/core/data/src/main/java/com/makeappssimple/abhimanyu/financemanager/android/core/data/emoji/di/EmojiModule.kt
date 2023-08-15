package com.makeappssimple.abhimanyu.financemanager.android.core.data.emoji.di

import com.makeappssimple.abhimanyu.financemanager.android.core.data.emoji.repository.EmojiRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.emoji.repository.EmojiRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.emoji.usecase.GetAllEmojisUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.emoji.usecase.GetAllEmojisUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.emoji.core.remote.datasource.EmojiDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class EmojiModule {
    @Provides
    fun providesEmojiRepository(
        emojiDataSource: EmojiDataSource,
    ): EmojiRepository {
        return EmojiRepositoryImpl(
            emojiDataSource = emojiDataSource,
        )
    }

    @Provides
    fun providesGetAllEmojisUseCase(
        emojiRepository: EmojiRepository,
    ): GetAllEmojisUseCase {
        return GetAllEmojisUseCaseImpl(
            emojiRepository = emojiRepository,
        )
    }
}

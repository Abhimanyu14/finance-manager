package com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.di

import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.datasource.local.EmojiDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.repository.EmojiRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.repository.EmojiRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.usecase.GetAllEmojisFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.usecase.GetAllEmojisFlowUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.usecase.GetAllEmojisUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.usecase.GetAllEmojisUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.local.database.MyRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class EmojiModule {
    @Singleton
    @Provides
    fun providesEmojiDao(
        myRoomDatabase: MyRoomDatabase,
    ): EmojiDao {
        return myRoomDatabase.emojiDao()
    }

    @Singleton
    @Provides
    fun providesEmojiRepository(
        emojiDao: EmojiDao,
    ): EmojiRepository {
        return EmojiRepositoryImpl(
            emojiDao = emojiDao,
        )
    }

    @Singleton
    @Provides
    fun providesGetAllEmojisFlowUseCase(
        emojiRepository: EmojiRepository,
    ): GetAllEmojisFlowUseCase {
        return GetAllEmojisFlowUseCaseImpl(
            emojiRepository = emojiRepository,
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

package com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.di

import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.datasource.local.EmojiDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.repository.EmojiRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.repository.EmojiRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.usecase.DeleteAllEmojisUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.usecase.DeleteAllEmojisUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.usecase.GetEmojisUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.usecase.GetEmojisUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.usecase.InsertEmojisUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.usecase.InsertEmojisUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.local.database.MyRoomDatabase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.local.datastore.MyDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class EmojiModule {

    @Provides
    fun providesEmojiDao(
        myRoomDatabase: MyRoomDatabase,
    ): EmojiDao {
        return myRoomDatabase.emojiDao()
    }

    @Provides
    fun providesEmojiRepository(
        emojiDao: EmojiDao,
    ): EmojiRepository {
        return EmojiRepositoryImpl(
            emojiDao = emojiDao,
        )
    }

    @Provides
    fun providesDeleteAllEmojisUseCase(
        dataStore: MyDataStore,
        emojiRepository: EmojiRepository,
    ): DeleteAllEmojisUseCase {
        return DeleteAllEmojisUseCaseImpl(
            dataStore = dataStore,
            emojiRepository = emojiRepository,
        )
    }

    @Provides
    fun providesGetEmojisUseCase(
        emojiRepository: EmojiRepository,
    ): GetEmojisUseCase {
        return GetEmojisUseCaseImpl(
            emojiRepository = emojiRepository,
        )
    }

    @Provides
    fun providesInsertEmojisUseCase(
        dataStore: MyDataStore,
        emojiRepository: EmojiRepository,
    ): InsertEmojisUseCase {
        return InsertEmojisUseCaseImpl(
            dataStore = dataStore,
            emojiRepository = emojiRepository,
        )
    }
}

package com.makeappssimple.abhimanyu.financemanager.android.core.database.source.di

import com.makeappssimple.abhimanyu.financemanager.android.core.database.local.database.MyRoomDatabase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.datasource.local.SourceDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.repository.SourceRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.repository.SourceRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.DeleteAllSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.DeleteAllSourcesUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.DeleteSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.DeleteSourcesUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.GetSourceUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.GetSourceUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.GetSourcesCountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.GetSourcesCountUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.GetSourcesTotalBalanceAmountValueUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.GetSourcesTotalBalanceAmountValueUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.GetSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.GetSourcesUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.InsertSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.InsertSourcesUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.UpdateSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.UpdateSourcesUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class SourceModule {

    @Provides
    fun providesSourceDao(
        myRoomDatabase: MyRoomDatabase,
    ): SourceDao {
        return myRoomDatabase.sourceDao()
    }

    @Provides
    fun providesSourceRepository(
        sourceDao: SourceDao,
    ): SourceRepository {
        return SourceRepositoryImpl(
            sourceDao = sourceDao,
        )
    }

    @Provides
    fun providesDeleteAllSourcesUseCase(
        sourceRepository: SourceRepository,
    ): DeleteAllSourcesUseCase {
        return DeleteAllSourcesUseCaseImpl(
            sourceRepository = sourceRepository,
        )
    }

    @Provides
    fun providesDeleteSourcesUseCase(
        sourceRepository: SourceRepository,
    ): DeleteSourcesUseCase {
        return DeleteSourcesUseCaseImpl(
            sourceRepository = sourceRepository,
        )
    }

    @Provides
    fun providesGetSourcesCountUseCase(
        sourceRepository: SourceRepository,
    ): GetSourcesCountUseCase {
        return GetSourcesCountUseCaseImpl(
            sourceRepository = sourceRepository,
        )
    }

    @Provides
    fun providesGetSourcesTotalBalanceAmountValueUseCase(
        sourceRepository: SourceRepository,
    ): GetSourcesTotalBalanceAmountValueUseCase {
        return GetSourcesTotalBalanceAmountValueUseCaseImpl(
            sourceRepository = sourceRepository,
        )
    }

    @Provides
    fun providesGetSourcesUseCase(
        sourceRepository: SourceRepository,
    ): GetSourcesUseCase {
        return GetSourcesUseCaseImpl(
            sourceRepository = sourceRepository,
        )
    }

    @Provides
    fun providesGetSourceUseCase(
        sourceRepository: SourceRepository,
    ): GetSourceUseCase {
        return GetSourceUseCaseImpl(
            sourceRepository = sourceRepository,
        )
    }

    @Provides
    fun providesInsertSourcesUseCase(
        sourceRepository: SourceRepository,
    ): InsertSourcesUseCase {
        return InsertSourcesUseCaseImpl(
            sourceRepository = sourceRepository,
        )
    }

    @Provides
    fun providesUpdateSourcesUseCase(
        sourceRepository: SourceRepository,
    ): UpdateSourcesUseCase {
        return UpdateSourcesUseCaseImpl(
            sourceRepository = sourceRepository,
        )
    }
}

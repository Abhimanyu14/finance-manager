package com.makeappssimple.abhimanyu.financemanager.android.core.data.source.di

import com.makeappssimple.abhimanyu.financemanager.android.core.data.preferences.repository.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.repository.SourceRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.repository.SourceRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.DeleteSourceUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.DeleteSourceUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.DeleteSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.DeleteSourcesUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.GetAllSourcesCountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.GetAllSourcesCountUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.GetAllSourcesFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.GetAllSourcesFlowUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.GetAllSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.GetAllSourcesUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.GetSourceUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.GetSourceUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.GetSourcesTotalBalanceAmountValueUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.GetSourcesTotalBalanceAmountValueUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.InsertSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.InsertSourcesUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.UpdateSourcesBalanceAmountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.UpdateSourcesBalanceAmountUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.UpdateSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.UpdateSourcesUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.SourceDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class SourceModule {
    @Provides
    fun providesSourceRepository(
        sourceDao: SourceDao,
    ): SourceRepository {
        return SourceRepositoryImpl(
            sourceDao = sourceDao,
        )
    }

    @Provides
    fun providesDeleteSourcesUseCase(
        myPreferencesRepository: MyPreferencesRepository,
        sourceRepository: SourceRepository,
    ): DeleteSourcesUseCase {
        return DeleteSourcesUseCaseImpl(
            myPreferencesRepository = myPreferencesRepository,
            sourceRepository = sourceRepository,
        )
    }

    @Provides
    fun providesDeleteSourceUseCase(
        myPreferencesRepository: MyPreferencesRepository,
        sourceRepository: SourceRepository,
    ): DeleteSourceUseCase {
        return DeleteSourceUseCaseImpl(
            myPreferencesRepository = myPreferencesRepository,
            sourceRepository = sourceRepository,
        )
    }

    @Provides
    fun providesGetAllSourcesCountUseCase(
        sourceRepository: SourceRepository,
    ): GetAllSourcesCountUseCase {
        return GetAllSourcesCountUseCaseImpl(
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
    fun providesGetAllSourcesFlowUseCase(
        sourceRepository: SourceRepository,
    ): GetAllSourcesFlowUseCase {
        return GetAllSourcesFlowUseCaseImpl(
            sourceRepository = sourceRepository,
        )
    }

    @Provides
    fun providesGetAllSourcesUseCase(
        sourceRepository: SourceRepository,
    ): GetAllSourcesUseCase {
        return GetAllSourcesUseCaseImpl(
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
        myPreferencesRepository: MyPreferencesRepository,
        sourceRepository: SourceRepository,
    ): InsertSourcesUseCase {
        return InsertSourcesUseCaseImpl(
            myPreferencesRepository = myPreferencesRepository,
            sourceRepository = sourceRepository,
        )
    }

    @Provides
    fun providesUpdateSourcesBalanceAmountUseCase(
        myPreferencesRepository: MyPreferencesRepository,
        sourceRepository: SourceRepository,
    ): UpdateSourcesBalanceAmountUseCase {
        return UpdateSourcesBalanceAmountUseCaseImpl(
            myPreferencesRepository = myPreferencesRepository,
            sourceRepository = sourceRepository,
        )
    }

    @Provides
    fun providesUpdateSourcesUseCase(
        myPreferencesRepository: MyPreferencesRepository,
        sourceRepository: SourceRepository,
    ): UpdateSourcesUseCase {
        return UpdateSourcesUseCaseImpl(
            myPreferencesRepository = myPreferencesRepository,
            sourceRepository = sourceRepository,
        )
    }
}

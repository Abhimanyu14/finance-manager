package com.makeappssimple.abhimanyu.financemanager.android.core.data.preferences.di

import com.makeappssimple.abhimanyu.financemanager.android.core.data.preferences.repository.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.preferences.repository.MyPreferencesRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyPreferencesDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class PreferencesModule {
    @Provides
    fun providesMyPreferencesRepository(
        myPreferencesDataSource: MyPreferencesDataSource,
    ): MyPreferencesRepository {
        return MyPreferencesRepositoryImpl(
            myPreferencesDataSource = myPreferencesDataSource,
        )
    }
}

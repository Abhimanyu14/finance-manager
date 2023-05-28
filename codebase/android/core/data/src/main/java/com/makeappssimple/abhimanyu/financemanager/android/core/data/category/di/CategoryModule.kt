package com.makeappssimple.abhimanyu.financemanager.android.core.data.category.di

import com.makeappssimple.abhimanyu.financemanager.android.core.data.category.repository.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.category.repository.CategoryRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.category.usecase.DeleteCategoryUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.category.usecase.DeleteCategoryUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.category.usecase.GetAllCategoriesFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.category.usecase.GetAllCategoriesFlowUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.category.usecase.GetAllCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.category.usecase.GetAllCategoriesUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.category.usecase.GetCategoryUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.category.usecase.GetCategoryUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.category.usecase.InsertCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.category.usecase.InsertCategoriesUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.category.usecase.UpdateCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.category.usecase.UpdateCategoriesUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.preferences.repository.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.CategoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class CategoryModule {
    @Provides
    fun providesCategoryRepository(
        categoryDao: CategoryDao,
    ): CategoryRepository {
        return CategoryRepositoryImpl(
            categoryDao = categoryDao,
        )
    }

    @Provides
    fun providesDeleteCategoryUseCase(
        categoryRepository: CategoryRepository,
        myPreferencesRepository: MyPreferencesRepository,
    ): DeleteCategoryUseCase {
        return DeleteCategoryUseCaseImpl(
            categoryRepository = categoryRepository,
            myPreferencesRepository = myPreferencesRepository,
        )
    }

    @Provides
    fun providesGetAllCategoriesFlowUseCase(
        categoryRepository: CategoryRepository,
    ): GetAllCategoriesFlowUseCase {
        return GetAllCategoriesFlowUseCaseImpl(
            categoryRepository = categoryRepository,
        )
    }

    @Provides
    fun providesGetAllCategoriesUseCase(
        categoryRepository: CategoryRepository,
    ): GetAllCategoriesUseCase {
        return GetAllCategoriesUseCaseImpl(
            categoryRepository = categoryRepository,
        )
    }

    @Provides
    fun providesGetCategoryUseCase(
        categoryRepository: CategoryRepository,
    ): GetCategoryUseCase {
        return GetCategoryUseCaseImpl(
            categoryRepository = categoryRepository,
        )
    }

    @Provides
    fun providesInsertCategoriesUseCase(
        categoryRepository: CategoryRepository,
        myPreferencesRepository: MyPreferencesRepository,
    ): InsertCategoriesUseCase {
        return InsertCategoriesUseCaseImpl(
            categoryRepository = categoryRepository,
            myPreferencesRepository = myPreferencesRepository,
        )
    }

    @Provides
    fun providesUpdateCategoriesUseCase(
        categoryRepository: CategoryRepository,
        myPreferencesRepository: MyPreferencesRepository,
    ): UpdateCategoriesUseCase {
        return UpdateCategoriesUseCaseImpl(
            categoryRepository = categoryRepository,
            myPreferencesRepository = myPreferencesRepository,
        )
    }
}

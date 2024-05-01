package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.di

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.category.CategoryRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.DeleteCategoryUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.DeleteCategoryUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.GetAllCategoriesFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.GetAllCategoriesFlowUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.GetAllCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.GetAllCategoriesUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.GetCategoryUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.GetCategoryUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.InsertCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.InsertCategoriesUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.UpdateCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.UpdateCategoriesUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

// TODO(Abhi): Remove interfaces for the use-cases and remove this module
@Module
@InstallIn(SingletonComponent::class)
public class CategoryUseCaseModule {
    @Provides
    public fun providesDeleteCategoryUseCase(
        categoryRepository: CategoryRepository,
        myPreferencesRepository: MyPreferencesRepository,
    ): DeleteCategoryUseCase {
        return DeleteCategoryUseCaseImpl(
            categoryRepository = categoryRepository,
            myPreferencesRepository = myPreferencesRepository,
        )
    }

    @Provides
    public fun providesGetAllCategoriesFlowUseCase(
        categoryRepository: CategoryRepository,
    ): GetAllCategoriesFlowUseCase {
        return GetAllCategoriesFlowUseCaseImpl(
            categoryRepository = categoryRepository,
        )
    }

    @Provides
    public fun providesGetAllCategoriesUseCase(
        categoryRepository: CategoryRepository,
    ): GetAllCategoriesUseCase {
        return GetAllCategoriesUseCaseImpl(
            categoryRepository = categoryRepository,
        )
    }

    @Provides
    public fun providesGetCategoryUseCase(
        categoryRepository: CategoryRepository,
    ): GetCategoryUseCase {
        return GetCategoryUseCaseImpl(
            categoryRepository = categoryRepository,
        )
    }

    @Provides
    public fun providesInsertCategoriesUseCase(
        categoryRepository: CategoryRepository,
        myPreferencesRepository: MyPreferencesRepository,
    ): InsertCategoriesUseCase {
        return InsertCategoriesUseCaseImpl(
            categoryRepository = categoryRepository,
            myPreferencesRepository = myPreferencesRepository,
        )
    }

    @Provides
    public fun providesUpdateCategoriesUseCase(
        categoryRepository: CategoryRepository,
        myPreferencesRepository: MyPreferencesRepository,
    ): UpdateCategoriesUseCase {
        return UpdateCategoriesUseCaseImpl(
            categoryRepository = categoryRepository,
            myPreferencesRepository = myPreferencesRepository,
        )
    }
}

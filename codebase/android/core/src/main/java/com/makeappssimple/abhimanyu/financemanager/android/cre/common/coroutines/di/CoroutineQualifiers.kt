package com.makeappssimple.abhimanyu.financemanager.android.cre.common.coroutines.di

import javax.inject.Qualifier

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
public annotation class DefaultDispatcher

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
public annotation class IoDispatcher

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
public annotation class MainDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
public annotation class MainImmediateDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
public annotation class UnconfinedDispatcher

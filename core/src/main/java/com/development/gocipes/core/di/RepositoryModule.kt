package com.development.gocipes.core.di

import com.development.gocipes.core.data.repository.ArticleRepositoryImpl
import com.development.gocipes.core.data.repository.AuthRepositoryImpl
import com.development.gocipes.core.data.repository.TechniqueRepositoryImpl
import com.development.gocipes.core.domain.repository.ArticleRepository
import com.development.gocipes.core.domain.repository.AuthRepository
import com.development.gocipes.core.domain.repository.TechniqueRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun provideTechniqueRepository(techniqueRepositoryImpl: TechniqueRepositoryImpl): TechniqueRepository

    @Binds
    @Singleton
    abstract fun provideArticleRepository(articleRepositoryImpl: ArticleRepositoryImpl): ArticleRepository
}
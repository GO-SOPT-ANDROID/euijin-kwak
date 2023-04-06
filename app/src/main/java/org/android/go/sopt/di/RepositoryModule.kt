package org.android.go.sopt.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.android.go.sopt.data.repository.UserRepositoryImpl
import org.android.go.sopt.domain.repository.UserRepository

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun provideRepositoryImpl(repositoryImpl: UserRepositoryImpl): UserRepository
}
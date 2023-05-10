package org.android.go.sopt.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.android.go.sopt.data.repository.KakaoRepositoryImpl
import org.android.go.sopt.data.repository.MusicRepositoryImpl
import org.android.go.sopt.domain.repository.KakaoRepository
import org.android.go.sopt.domain.repository.MusicRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {

    @Binds
    @Singleton
    fun bindMusicRepositoryImpl(repositoryImpl: MusicRepositoryImpl): MusicRepository

    @Binds
    @Singleton
    fun bindKakaoRepositoryImpl(repositoryImpl: KakaoRepositoryImpl): KakaoRepository

}
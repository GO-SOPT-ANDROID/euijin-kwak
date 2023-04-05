package org.android.go.sopt.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun updateUser(user: UserEntity)

    suspend fun readUser(): Flow<UserEntity>
}
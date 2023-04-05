package org.android.go.sopt.data.repository

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.android.go.sopt.UserPreferences
import org.android.go.sopt.domain.repository.UserEntity
import org.android.go.sopt.domain.repository.UserRepository

class UserRepositoryImpl(
    private val protoDataStore: DataStore<UserPreferences>,
): UserRepository {
    override suspend fun updateUser(user: UserEntity) {
        protoDataStore.updateData { preference ->
            preference.toBuilder()
                .setId(user.id)
                .setPassword(user.password)
                .setName(user.name)
                .setSpecialty(user.specialty)
                .build()
        }
    }

    override suspend fun readUser(): Flow<UserEntity> {
        return protoDataStore.data.map {
            UserEntity(it.id, it.password, it.name, it.specialty)
        }
    }
}
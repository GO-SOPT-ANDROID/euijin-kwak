package org.android.go.sopt.data.repository

import org.android.go.sopt.data.model.reqres.toReqresEntity
import org.android.go.sopt.data.service.reqres.ReqresService
import org.android.go.sopt.domain.entity.reqres.ReqresEntity
import org.android.go.sopt.domain.repository.ReqresRepository
import javax.inject.Inject

class ReqresRepositoryImpl @Inject constructor(private val reqresService: ReqresService): ReqresRepository {
    override suspend fun getUsers(page: Int): ReqresEntity? {
        val response = reqresService.getUsers(page)
        return if (response.isSuccessful) {
            response.body()?.toReqresEntity()
        } else {
            null
        }
    }
}
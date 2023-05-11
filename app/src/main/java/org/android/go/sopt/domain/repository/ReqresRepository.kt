package org.android.go.sopt.domain.repository

import org.android.go.sopt.domain.entity.reqres.ReqresEntity

interface ReqresRepository {
    suspend fun getUsers(page: Int): ReqresEntity?
}
package com.example.githubrepositories.data

import com.example.githubrepositories.data.remote.RemoteDataSource
import com.example.githubrepositories.model.BaseApiResponse
import com.example.githubrepositories.model.GithubResponse
import com.example.githubrepositories.service.ApiResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : BaseApiResponse() {

    suspend fun getRepositories(): Flow<ApiResult<GithubResponse>> {
        return flow {
            emit(safeApiCall { remoteDataSource.getRepos() })
        }.flowOn(Dispatchers.IO)
    }
}
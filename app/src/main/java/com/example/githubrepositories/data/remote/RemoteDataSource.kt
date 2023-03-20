package com.example.githubrepositories.data.remote

import com.example.githubrepositories.service.RepositoryService
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val repositoryService: RepositoryService) {

    suspend fun getRepos() = repositoryService.getRepositories("kotlin")
}
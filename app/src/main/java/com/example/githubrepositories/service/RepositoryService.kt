package com.example.githubrepositories.service

import com.example.githubrepositories.model.GithubResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RepositoryService {
    @GET("search/repositories?")
    suspend fun getRepositories(@Query("q") q : String) : Response<GithubResponse>
}
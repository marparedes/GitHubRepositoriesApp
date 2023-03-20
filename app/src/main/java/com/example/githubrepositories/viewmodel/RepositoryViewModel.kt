package com.example.githubrepositories.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.githubrepositories.data.Repository
import com.example.githubrepositories.model.GithubResponse
import com.example.githubrepositories.service.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoryViewModel @Inject constructor (
    private val repository: Repository,
    application : Application
) : AndroidViewModel(application) {

    private val _response: MutableLiveData<ApiResult<GithubResponse>> = MutableLiveData()
    val response: LiveData<ApiResult<GithubResponse>> = _response

    fun fetchGitHubApi() {
        _response.value = ApiResult.Loading(null, isLoading = true)
        viewModelScope.launch {
            repository.getRepositories().collect { values ->
                _response.value = values
            }
        }
    }
}
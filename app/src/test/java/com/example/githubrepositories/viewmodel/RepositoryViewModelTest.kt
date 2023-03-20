package com.example.githubrepositories.viewmodel

import android.app.Application
import androidx.lifecycle.Observer
import com.example.githubrepositories.base.BaseUnitTest
import com.example.githubrepositories.data.Repository
import com.example.githubrepositories.model.GithubResponse
import com.example.githubrepositories.service.ApiResult
import io.mockk.MockKAnnotations
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito
import org.mockito.Mock
import org.mockito.Mockito


class RepositoryViewModelTest: BaseUnitTest() {

    @Mock
    lateinit var repository: Repository

    @Mock
    lateinit var application : Application

    @Mock
    private lateinit var responseLiveDataObserver : Observer<ApiResult<GithubResponse>>

    lateinit var repositoryViewModel: RepositoryViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repositoryViewModel = Mockito.spy(
            RepositoryViewModel(repository, application)
        )

        repositoryViewModel.response.observeForever(responseLiveDataObserver)
    }

    @After
    override fun tearDown(){
        super.tearDown()
        repositoryViewModel.response.removeObserver(responseLiveDataObserver)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when the api call return error`() = testDispatcher.runBlockingTest {
        val expectedResult = ApiResult.Error("body")

        val expectedFlow = flow { emit(expectedResult) }

        BDDMockito.given(repository.getRepositories()).willReturn(expectedFlow)

        repositoryViewModel.fetchGitHubApi()
        Mockito.verify(repository).getRepositories()
        Mockito.verify(responseLiveDataObserver, Mockito.times(1))
            .onChanged(Mockito.isA(ApiResult.Loading(null, true)::class.java))
        Mockito.verify(responseLiveDataObserver, Mockito.times(1))
            .onChanged(Mockito.isA(ApiResult.Error("Error")::class.java))
        Mockito.verifyNoMoreInteractions(responseLiveDataObserver)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when the api call return success`() = testDispatcher.runBlockingTest {
        val result = GithubResponse(incomplete_results = false, items = emptyList(), total_count = 30)
        val expectedResult = ApiResult.Success(result)
        val expectedFlow = flow { emit(expectedResult) }

        BDDMockito.given(repository.getRepositories()).willReturn(expectedFlow)

        repositoryViewModel.fetchGitHubApi()
        Mockito.verify(repository).getRepositories()
        Mockito.verify(responseLiveDataObserver, Mockito.times(1))
            .onChanged(Mockito.isA(ApiResult.Loading(null, true)::class.java))
        Mockito.verify(responseLiveDataObserver, Mockito.times(1))
            .onChanged(Mockito.isA(ApiResult.Success(result)::class.java))
        Mockito.verifyNoMoreInteractions(responseLiveDataObserver)
    }

}
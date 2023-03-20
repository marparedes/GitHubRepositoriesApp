package com.example.githubrepositories.model

import androidx.annotation.Keep

@Keep
data class GithubResponse(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
)
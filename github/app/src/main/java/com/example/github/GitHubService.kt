package com.example.github

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubService {
    @GET("search/repositories")
    fun searchRepositories(@Query("q") query: String): Call<RepoSearchResponse>
}

data class RepoSearchResponse(
    val items: List<Repo>
)

data class Repo(
    val name: String,
    val owner: Owner,
    val language: String,
    val stargazers_count: Int,
    val html_url: String
)

data class Owner(
    val login: String
)



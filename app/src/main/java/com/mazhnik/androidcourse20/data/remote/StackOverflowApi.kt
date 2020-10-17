/*
 * Created by p-mazhnik 10/17/2020.
 * https://github.com/p-mazhnik
 */

package com.mazhnik.androidcourse20.data.remote

import retrofit2.Response
import retrofit2.http.GET

interface StackOverflowApi {
    @GET("/2.2/questions?page=1&pagesize=100&order=desc&sort=activity&tagged=Android&site=stackoverflow")
    suspend fun getQuestions(): Response<ListResponse>

    companion object {
        const val STACKOVERFLOW_API_URL = "https://api.stackexchange.com/2.2/"
    }
}


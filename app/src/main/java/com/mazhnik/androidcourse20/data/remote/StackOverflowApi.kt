/*
 * Created by p-mazhnik 10/17/2020.
 * https://github.com/p-mazhnik
 */

package com.mazhnik.androidcourse20.data.remote

import com.mazhnik.androidcourse20.data.model.Answer
import com.mazhnik.androidcourse20.data.model.Question
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface StackOverflowApi {
    @GET("/questions?page=1&pagesize=100&order=asc&sort=creation&tagged=Android&site=stackoverflow&filter=withbody")
    suspend fun getQuestions(): Response<ListResponse<Question>>

    @GET("/questions/{questionId}/answers?order=desc&sort=votes&site=stackoverflow&filter=withbody")
    suspend fun getAnswers(@Path("questionId") questionId: Long): Response<ListResponse<Answer>>

    companion object {
        const val STACKOVERFLOW_API_URL = "https://api.stackexchange.com/2.2/"
    }
}


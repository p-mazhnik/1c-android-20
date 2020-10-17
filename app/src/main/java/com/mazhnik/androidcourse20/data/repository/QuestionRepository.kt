/*
 * Created by p-mazhnik 10/17/2020.
 * https://github.com/p-mazhnik
 */

package com.mazhnik.androidcourse20.data.repository

import android.util.Log
import com.mazhnik.androidcourse20.data.local.dao.QuestionDao
import com.mazhnik.androidcourse20.data.model.Question
import com.mazhnik.androidcourse20.data.remote.ListResponse
import com.mazhnik.androidcourse20.data.remote.StackOverflowApi
import com.mazhnik.androidcourse20.data.utils.NetworkBoundResource
import com.mazhnik.androidcourse20.data.utils.State
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class QuestionRepository (
    private val localDataSource: QuestionDao,
    private val networkDataSource: StackOverflowApi
) {
    companion object {
        const val TAG = "QuestionRepository"
    }

    val networkBoundResource = object : NetworkBoundResource<List<Question>, ListResponse>() {

        override suspend fun saveRemoteData(response: ListResponse) {
            Log.d(TAG, response.toString())
            return localDataSource.addQuestions(response.items)
        }

        override fun fetchFromLocal(): Flow<List<Question>> = localDataSource.getAllQuestions()

        override suspend fun fetchFromRemote(): Response<ListResponse> {
            val remote = networkDataSource.getQuestions()
            Log.d(TAG, remote.toString())
            return networkDataSource.getQuestions()
        }
    }

    fun getAllQuestions(): Flow<State<List<Question>>> {
        return networkBoundResource.asFlow()
    }
}


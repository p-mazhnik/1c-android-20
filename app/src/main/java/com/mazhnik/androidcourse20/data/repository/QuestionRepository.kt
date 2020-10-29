/*
 * Created by p-mazhnik 10/17/2020.
 * https://github.com/p-mazhnik
 */

package com.mazhnik.androidcourse20.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mazhnik.androidcourse20.data.local.dao.QuestionDao
import com.mazhnik.androidcourse20.data.model.Answer
import com.mazhnik.androidcourse20.data.model.Question
import com.mazhnik.androidcourse20.data.remote.ListResponse
import com.mazhnik.androidcourse20.data.remote.StackOverflowApi
import com.mazhnik.androidcourse20.data.utils.NetworkBoundResource
import com.mazhnik.androidcourse20.data.utils.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class QuestionRepository (
    private val localDataSource: QuestionDao,
    private val networkDataSource: StackOverflowApi
) {
    companion object {
        const val TAG = "QuestionRepository"
    }

    val selectedQuestion = MutableLiveData<Question>()

    private val questionsResource =
        object : NetworkBoundResource<List<Question>, ListResponse<Question>>() {

            override suspend fun saveRemoteData(response: List<Question>) {
                Log.d(TAG, response.toString())
                return localDataSource.addQuestions(response)
            }

            override fun fetchFromLocal(): Flow<List<Question>> = localDataSource.getAllQuestions()

            override suspend fun fetchFromRemote(): Response<ListResponse<Question>> {
                val remote = networkDataSource.getQuestions()
                Log.d(TAG, remote.toString())
                return remote
            }

            override fun shouldSaveToLocal(): Boolean = true

            override fun remoteToLocal(remote: ListResponse<Question>): List<Question> {
                return remote.items
            }
        }

    fun getAllQuestions(): Flow<State<List<Question>>> {
        return questionsResource.asFlow()
    }

    class AnswersResource(
        private val networkDataSource: StackOverflowApi,
        private val questionId: Long,
    ): NetworkBoundResource<List<Answer>, ListResponse<Answer>>() {

        override suspend fun saveRemoteData(response: List<Answer>) {
            Log.d(TAG, response.toString())
            return
        }

        override fun fetchFromLocal(): Flow<List<Answer>> = flow {emit(emptyList<Answer>())}

        override suspend fun fetchFromRemote(): Response<ListResponse<Answer>> {
            return networkDataSource.getAnswers(questionId)
        }

        override fun shouldSaveToLocal(): Boolean = false

        override fun remoteToLocal(remote: ListResponse<Answer>): List<Answer> {
            return remote.items
        }
    }

    fun getAnswers(questionId: Long): Flow<State<List<Answer>>> {
        val answersResource = AnswersResource(networkDataSource, questionId)
        return answersResource.asFlow()
    }
}


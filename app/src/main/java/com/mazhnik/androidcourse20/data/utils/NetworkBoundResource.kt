/*
 * Created by p-mazhnik 10/17/2020.
 * https://github.com/p-mazhnik
 */

package com.mazhnik.androidcourse20.data.utils

import kotlinx.coroutines.flow.*
import retrofit2.Response
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread

abstract class NetworkBoundResource<RES, REQ> {

    fun asFlow() = flow<State<RES>> {

        // Emit Loading State
        emit(State.loading())

        // Emit Database content first
        emit(State.success(fetchFromLocal().first()))

        // Fetch latest posts from remote
        val apiResponse = fetchFromRemote()

        // Parse body
        val remotePosts = apiResponse.body()

        // Check for response validation
        if (apiResponse.isSuccessful && remotePosts != null) {
            // Save posts into the persistence storage
            saveRemoteData(remotePosts)
        } else {
            // Something went wrong! Emit Error state.
            emit(State.error(apiResponse.message()))
        }

        // Retrieve posts from persistence storage and emit
        emitAll(
            fetchFromLocal().map {
                State.success(it)
            }
        )
    }.catch { e ->
        emit(State.error(e.message ?: ""))
    }

    @WorkerThread
    protected abstract suspend fun saveRemoteData(response: REQ)

    @MainThread
    protected abstract fun fetchFromLocal(): Flow<RES>

    @WorkerThread
    protected abstract suspend fun fetchFromRemote(): Response<REQ>
}


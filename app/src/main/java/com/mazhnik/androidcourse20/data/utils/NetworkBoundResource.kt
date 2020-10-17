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
        val shouldSave = shouldSaveToLocal()
        // Emit Loading State
        emit(State.loading())

        // Emit Database content first
        if (shouldSave) {
            emit(State.success(fetchFromLocal().first()))
        }

        // Fetch data from remote
        val apiResponse = fetchFromRemote()
        val remoteData = apiResponse.body()

        // Check for response validation
        if (apiResponse.isSuccessful && remoteData != null) {
            if(shouldSave) {
                saveRemoteData(remoteToLocal(remoteData))
            } else {
                emit(State.success(remoteToLocal(remoteData)))
            }
        } else {
            emit(State.error(apiResponse.message()))
        }

        if (shouldSave) {
            emitAll(
                fetchFromLocal().map {
                    State.success(it)
                }
            )
        }
    }.catch { e ->
        emit(State.error(e.message ?: ""))
    }

    @WorkerThread
    protected abstract suspend fun saveRemoteData(response: RES)

    @MainThread
    protected abstract fun fetchFromLocal(): Flow<RES>

    @WorkerThread
    protected abstract suspend fun fetchFromRemote(): Response<REQ>

    protected abstract fun shouldSaveToLocal(): Boolean

    protected abstract fun remoteToLocal(remote: REQ): RES
}


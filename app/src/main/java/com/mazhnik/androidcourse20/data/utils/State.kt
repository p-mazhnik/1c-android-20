/*
 * Created by p-mazhnik 10/17/2020.
 * https://github.com/p-mazhnik
 */

package com.mazhnik.androidcourse20.data.utils

sealed class State<T> {
    class Loading<T> : State<T>()

    data class Success<T>(val data: T) : State<T>()

    data class Error<T>(val message: String) : State<T>()

    companion object {
        fun <T> loading() = Loading<T>()

        fun <T> success(data: T) =
            Success(data)

        fun <T> error(message: String) =
            Error<T>(message)
    }
}


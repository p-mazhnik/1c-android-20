/*
 * Created by p-mazhnik 10/17/2020.
 * https://github.com/p-mazhnik
 */

package com.mazhnik.androidcourse20.data.remote

import com.google.gson.annotations.SerializedName
import com.mazhnik.androidcourse20.data.model.Question

data class ListResponse<T> (
    @SerializedName("items") val items: List<T>
)

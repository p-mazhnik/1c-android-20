/*
 * Created by p-mazhnik 10/17/2020.
 * https://github.com/p-mazhnik
 */

package com.mazhnik.androidcourse20.data.model

import android.text.Spanned
import com.google.gson.annotations.SerializedName
import org.sufficientlysecure.htmltextview.HtmlFormatter
import org.sufficientlysecure.htmltextview.HtmlFormatterBuilder


data class Answer(
    @SerializedName("body") val body: String,
    @SerializedName("score") val score: Int,
    @SerializedName("question_id") val questionId: Long,
    @SerializedName("answer_id") val id: Long,
) {
    fun getHtmlBody(): Spanned {
        return HtmlFormatter.formatHtml(HtmlFormatterBuilder().setHtml(body))
    }
}


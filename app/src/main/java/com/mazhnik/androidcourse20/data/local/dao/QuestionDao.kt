/*
 * Created by p-mazhnik 10/17/2020.
 * https://github.com/p-mazhnik
 */

package com.mazhnik.androidcourse20.data.local.dao

import androidx.room.*
import com.mazhnik.androidcourse20.data.model.Question
import kotlinx.coroutines.flow.Flow

@Dao
abstract interface QuestionDao {

    @Query("SELECT * FROM question")
    abstract fun getAllQuestions(): Flow<List<Question>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addQuestion(question: Question)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addQuestions(questions: List<Question>)

}


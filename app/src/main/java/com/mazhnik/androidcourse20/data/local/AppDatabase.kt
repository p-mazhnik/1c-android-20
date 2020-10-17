/*
 * Created by p-mazhnik 10/17/2020.
 * https://github.com/p-mazhnik
 */

package com.mazhnik.androidcourse20.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mazhnik.androidcourse20.data.model.Question
import com.mazhnik.androidcourse20.data.local.dao.QuestionDao

@Database(entities = [Question::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val questionDao: QuestionDao
}

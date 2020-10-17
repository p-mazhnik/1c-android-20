/*
 * Created by p-mazhnik 10/17/2020.
 * https://github.com/p-mazhnik
 */

package com.mazhnik.androidcourse20.di

import android.app.Application
import androidx.room.Room
import com.mazhnik.androidcourse20.data.local.AppDatabase
import com.mazhnik.androidcourse20.data.local.dao.QuestionDao
import com.mazhnik.androidcourse20.data.remote.StackOverflowApi
import com.mazhnik.androidcourse20.data.repository.QuestionRepository
import com.mazhnik.androidcourse20.ui.QuestionListViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.Gson
import com.mazhnik.androidcourse20.ui.AnswerListViewModel
import okhttp3.OkHttpClient

val viewModelModule = module {
    viewModel { QuestionListViewModel(get()) }
    viewModel { AnswerListViewModel(get()) }
}

val repositoryModule = module {
    single { QuestionRepository(get(), get()) }
}

val databaseModule = module {

    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "tonometer")
            .build()
    }


    fun provideQuestionDao(database: AppDatabase): QuestionDao {
        return database.questionDao
    }

    single { provideDatabase(androidApplication()) }
    single { provideQuestionDao(get()) }
}

val networkModule = module {
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder().build()
    }
    fun provideRetrofitService(okHttpClient: OkHttpClient): StackOverflowApi = Retrofit.Builder()
        .baseUrl(StackOverflowApi.STACKOVERFLOW_API_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .build()
        .create(StackOverflowApi::class.java)

    factory { provideOkHttpClient() }
    single { provideRetrofitService(get()) }
}


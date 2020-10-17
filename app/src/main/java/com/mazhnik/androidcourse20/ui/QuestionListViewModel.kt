/*
 * Created by p-mazhnik 10/17/2020.
 * https://github.com/p-mazhnik
 */

package com.mazhnik.androidcourse20.ui

import androidx.lifecycle.*
import com.mazhnik.androidcourse20.data.model.Question
import com.mazhnik.androidcourse20.data.repository.QuestionRepository
import com.mazhnik.androidcourse20.data.utils.State
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

class QuestionListViewModel(
    private val questionRepository: QuestionRepository,
): ViewModel() {
    private val _state = MutableLiveData<State<List<Question>>>()
    val state: LiveData<State<List<Question>>>
        get() = _state

    init {
        _state.value = State.loading()
    }

    fun getQuestions() {
        viewModelScope.launch {
            questionRepository.getAllQuestions().collect {
                _state.value = it
            }
        }
    }

    fun select(question: Question) {
        questionRepository.selectedQuestion.postValue(question)
    }
}


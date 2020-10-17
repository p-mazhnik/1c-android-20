/*
 * Created by p-mazhnik 10/17/2020.
 * https://github.com/p-mazhnik
 */

package com.mazhnik.androidcourse20.ui

import androidx.lifecycle.*
import com.mazhnik.androidcourse20.data.model.Answer
import com.mazhnik.androidcourse20.data.model.Question
import com.mazhnik.androidcourse20.data.repository.QuestionRepository
import com.mazhnik.androidcourse20.data.utils.State
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

class AnswerListViewModel(
    private val questionRepository: QuestionRepository,
): ViewModel() {
    val question = questionRepository.selectedQuestion

    private val _state = MutableLiveData<State<List<Answer>>>()
    val state: LiveData<State<List<Answer>>>
        get() = _state

    init {
        _state.value = State.loading()
    }

    fun getAnswers() {
        viewModelScope.launch {
            val question = question.value ?: return@launch
            questionRepository.getAnswers(question.id).collect {
                _state.value = it
            }
        }
    }
}


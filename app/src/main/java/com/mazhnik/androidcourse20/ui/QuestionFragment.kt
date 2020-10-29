/*
 * Created by p-mazhnik 10/17/2020.
 * https://github.com/p-mazhnik
 */

package com.mazhnik.androidcourse20.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.mazhnik.androidcourse20.data.model.Answer
import com.mazhnik.androidcourse20.data.utils.State
import com.mazhnik.androidcourse20.databinding.FragmentQuestionBinding
import com.mazhnik.androidcourse20.BR
import com.mazhnik.androidcourse20.MainActivity
import com.mazhnik.androidcourse20.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class QuestionFragment : Fragment() {
    private lateinit var binding: FragmentQuestionBinding
    private val viewModel: AnswerListViewModel by viewModel()
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuestionBinding.inflate(
            inflater, container, false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        binding.setVariable(
            BR.viewModel,
            viewModel
        )
        showLoadingState(true)
        return binding.root
    }

    private fun setListAdapter(answers: List<Answer>) {
        viewAdapter = AnswerRecyclerViewAdapter(viewLifecycleOwner, answers, viewModel)
        recyclerView.adapter = viewAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewManager = LinearLayoutManager(activity)

        recyclerView = binding.answerList.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
        }

        setListAdapter(emptyList())
        viewModel.getAnswers()
        viewModel.state.observe(viewLifecycleOwner, {viewState ->
            viewState?.let {
                when(viewState) {
                    is State.Loading -> {
                        showLoadingState(true)
                    }
                    is State.Success -> {
                        showLoadingState(false)
                        setListAdapter(viewState.data)
                    }
                    is State.Error -> {
                        showLoadingState(false)
                        Snackbar.make(view, viewState.message, Snackbar.LENGTH_SHORT)
                            .setAction(R.string.snackbar_retry_action_text) {
                                viewModel.getAnswers()
                            }
                            .show()
                    }
                }
            }
        })

        (activity as MainActivity).setOnRefreshListener {
            viewModel.getAnswers()
        }
    }

    private fun showLoadingState(isLoading: Boolean) {
        (activity as MainActivity).showLoadingState(isLoading)
    }
}


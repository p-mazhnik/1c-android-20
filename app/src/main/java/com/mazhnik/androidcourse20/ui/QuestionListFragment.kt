/*
 * Created by p-mazhnik 10/17/2020.
 * https://github.com/p-mazhnik
 */

package com.mazhnik.androidcourse20.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.mazhnik.androidcourse20.MainActivity
import com.mazhnik.androidcourse20.R
import com.mazhnik.androidcourse20.data.model.Question
import com.mazhnik.androidcourse20.data.utils.State
import com.mazhnik.androidcourse20.databinding.FragmentQuestionListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class QuestionListFragment : Fragment() {
    private val viewModel: QuestionListViewModel by viewModel()
    private lateinit var binding: FragmentQuestionListBinding

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuestionListBinding.inflate(
            inflater, container, false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        showLoadingState(true)
        return binding.root
    }

    private fun setListAdapter(questions: List<Question>) {
        viewAdapter = QuestionRecyclerViewAdapter(viewLifecycleOwner, questions, viewModel, findNavController())
        recyclerView.adapter = viewAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewManager = LinearLayoutManager(activity)

        recyclerView = binding.questionList.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
        }

        setListAdapter(emptyList())
        viewModel.state.observe(viewLifecycleOwner, {viewState ->
            viewState?.let {
                when(viewState) {
                    is State.Loading -> {
                        showLoadingState(true)
                    }
                    is State.Success -> {
                        showLoadingState(false)
                        Log.d("QuestionListFragment", viewState.toString())
                        setListAdapter(viewState.data)
                    }
                    is State.Error -> {
                        showLoadingState(false)
                        Snackbar.make(view, viewState.message, Snackbar.LENGTH_SHORT)
                            .setAction(R.string.snackbar_retry_action_text) {
                                viewModel.getQuestions()
                            }
                            .show()
                    }
                }
            }
        })

        (activity as MainActivity).setOnRefreshListener {
            viewModel.getQuestions()
        }
    }

    private fun showLoadingState(isLoading: Boolean) {
        (activity as MainActivity).showLoadingState(isLoading)
    }
}


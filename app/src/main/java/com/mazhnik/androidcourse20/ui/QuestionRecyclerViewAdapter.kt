/*
 * Created by p-mazhnik 10/17/2020.
 * https://github.com/p-mazhnik
 */

package com.mazhnik.androidcourse20.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.mazhnik.androidcourse20.data.model.Question
import com.mazhnik.androidcourse20.databinding.QuestionItemBinding

class QuestionRecyclerViewAdapter(
    private val viewLifecycleOwner: LifecycleOwner,
    private val questionList: List<Question>?,
    private val viewModel: QuestionListViewModel,
    private val navController: NavController,
) :
    RecyclerView.Adapter<QuestionRecyclerViewAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QuestionRecyclerViewAdapter.MyViewHolder {
        val binding =
            QuestionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return MyViewHolder(
            binding, viewModel
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if(questionList != null) {
            holder.bind(questionList[position])
            holder.itemView.setOnClickListener{
                viewModel.select(questionList[position])
                val action = QuestionListFragmentDirections.actionQuestionListFragmentToQuestionFragment()
                navController.navigate(action)
            }
        }
    }

    override fun getItemCount() : Int {
        return questionList?.size ?: 0
    }

    class MyViewHolder(
        private val binding: QuestionItemBinding,
        private val viewModel: QuestionListViewModel,
    ): RecyclerView.ViewHolder(
        binding.root
    ) {

        fun bind(question: Question) {
            binding.question = question
            binding.executePendingBindings()
        }
    }
}


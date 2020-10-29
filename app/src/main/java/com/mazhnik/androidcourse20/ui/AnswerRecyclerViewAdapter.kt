/*
 * Created by p-mazhnik 10/17/2020.
 * https://github.com/p-mazhnik
 */

package com.mazhnik.androidcourse20.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.mazhnik.androidcourse20.data.model.Answer
import com.mazhnik.androidcourse20.databinding.AnswerItemBinding
import com.mazhnik.androidcourse20.databinding.QuestionBodyBinding

class AnswerRecyclerViewAdapter(
    private val viewLifecycleOwner: LifecycleOwner,
    private val list: List<Answer>?,
    private val viewModel: AnswerListViewModel,
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_ANSWER = 1
    private val VIEW_TYPE_QUESTION = 2

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val binding: ViewDataBinding
        when (viewType) {
            VIEW_TYPE_ANSWER -> {
                binding =
                    AnswerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                binding.lifecycleOwner = viewLifecycleOwner
                return AnswerViewHolder(
                    binding, viewModel
                )
            }
            else -> {
                binding =
                    QuestionBodyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                binding.lifecycleOwner = viewLifecycleOwner
                return QuestionBodyViewHolder(
                    binding, viewModel
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            VIEW_TYPE_QUESTION -> {
                (holder as QuestionBodyViewHolder).bind()
            }
            else -> {
                if(list != null) {
                    (holder as AnswerViewHolder).bind(list[position - 1])
                }
            }
        }
    }

    override fun getItemCount() : Int {
        val count = list?.size ?: 0
        return count + 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> VIEW_TYPE_QUESTION
            else -> VIEW_TYPE_ANSWER
        }
    }

    class QuestionBodyViewHolder(
        private val binding: QuestionBodyBinding,
        private val viewModel: AnswerListViewModel,
    ): RecyclerView.ViewHolder(
        binding.root
    ) {

        fun bind() {
            binding.viewModel = viewModel
            binding.executePendingBindings()
        }
    }

    class AnswerViewHolder(
        private val binding: AnswerItemBinding,
        private val viewModel: AnswerListViewModel,
    ): RecyclerView.ViewHolder(
        binding.root
    ) {

        fun bind(answer: Answer) {
            binding.answer = answer
            binding.executePendingBindings()
        }
    }
}


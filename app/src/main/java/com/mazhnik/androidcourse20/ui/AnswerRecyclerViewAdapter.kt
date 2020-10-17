/*
 * Created by p-mazhnik 10/17/2020.
 * https://github.com/p-mazhnik
 */

package com.mazhnik.androidcourse20.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.mazhnik.androidcourse20.data.model.Answer
import com.mazhnik.androidcourse20.databinding.AnswerItemBinding

class AnswerRecyclerViewAdapter(
    private val viewLifecycleOwner: LifecycleOwner,
    private val list: List<Answer>?,
    private val viewModel: AnswerListViewModel,
) :
    RecyclerView.Adapter<AnswerRecyclerViewAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding =
            AnswerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return MyViewHolder(
            binding, viewModel
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if(list != null) {
            holder.bind(list[position])
        }
    }

    override fun getItemCount() : Int {
        return list?.size ?: 0
    }

    class MyViewHolder(
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


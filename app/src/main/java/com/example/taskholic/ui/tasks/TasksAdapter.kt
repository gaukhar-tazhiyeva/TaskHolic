package com.example.taskholic.ui.tasks

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taskholic.data.local.entity.TaskEntity
import com.example.taskholic.databinding.ItemTaskBinding

class TasksAdapter(
    private val onTaskClick: (TaskEntity) -> Unit,
    private val onTaskLongClick: (TaskEntity, View) -> Unit // добавляем callback для long click
) : ListAdapter<TaskEntity, TasksAdapter.TaskViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TaskViewHolder(
        private val binding: ItemTaskBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(task: TaskEntity) {
            binding.tvTaskTitle.text = task.title

            if (task.isCompleted) {
                binding.tvTaskTitle.paintFlags =
                    binding.tvTaskTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                binding.tvTaskTitle.alpha = 0.5f
                binding.btnCheck.setImageResource(
                    com.example.taskholic.R.drawable.bg_checkbox_checked
                )
            } else {
                binding.tvTaskTitle.paintFlags =
                    binding.tvTaskTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                binding.tvTaskTitle.alpha = 1f
                binding.btnCheck.setImageResource(
                    com.example.taskholic.R.drawable.bg_checkbox_unchecked
                )
            }

            // обычный клик
            binding.btnCheck.setOnClickListener {
                onTaskClick(task)
            }

            // длинное нажатие для редактирования/удаления
            binding.root.setOnLongClickListener {
                onTaskLongClick(task, it)
                true
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<TaskEntity>() {
            override fun areItemsTheSame(old: TaskEntity, new: TaskEntity): Boolean =
                old.taskId == new.taskId

            override fun areContentsTheSame(old: TaskEntity, new: TaskEntity): Boolean =
                old == new
        }
    }
}

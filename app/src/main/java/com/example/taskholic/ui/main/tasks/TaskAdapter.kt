package com.example.taskholic.ui.tasks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taskholic.R
import com.example.taskholic.data.local.entity.TaskEntity

class TaskAdapter(
    private val onToggle: (TaskEntity, Boolean) -> Unit,
    private val onDelete: (TaskEntity) -> Unit
) : ListAdapter<TaskEntity, TaskAdapter.VH>(DIFF) {

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<TaskEntity>() {
            override fun areItemsTheSame(a: TaskEntity, b: TaskEntity) = a.taskId == b.taskId
            override fun areContentsTheSame(a: TaskEntity, b: TaskEntity) = a == b
        }
    }

    inner class VH(v: View) : RecyclerView.ViewHolder(v) {
        private val cb = v.findViewById<CheckBox>(R.id.cbDone)
        private val title = v.findViewById<TextView>(R.id.tvTitle)
        private val delete = v.findViewById<ImageButton>(R.id.btnDelete)

        fun bind(item: TaskEntity) {
            title.text = item.title
            cb.setOnCheckedChangeListener(null)
            cb.isChecked = item.isCompleted
            cb.setOnCheckedChangeListener { _, checked -> onToggle(item, checked) }
            delete.setOnClickListener { onDelete(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) =
        holder.bind(getItem(position))
}

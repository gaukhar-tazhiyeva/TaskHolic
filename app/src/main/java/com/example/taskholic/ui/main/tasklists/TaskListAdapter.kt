package com.example.taskholic.ui.tasklists

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taskholic.R
import com.example.taskholic.data.local.entity.TaskListEntity

class TaskListAdapter(
    private val onClick: (TaskListEntity) -> Unit,
    private val onDelete: (TaskListEntity) -> Unit
) : ListAdapter<TaskListEntity, TaskListAdapter.VH>(DIFF) {

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<TaskListEntity>() {
            override fun areItemsTheSame(a: TaskListEntity, b: TaskListEntity) = a.listId == b.listId
            override fun areContentsTheSame(a: TaskListEntity, b: TaskListEntity) = a == b
        }
    }

    inner class VH(v: View) : RecyclerView.ViewHolder(v) {
        private val name = v.findViewById<TextView>(R.id.tvName)
        private val delete = v.findViewById<ImageButton>(R.id.btnDelete)

        fun bind(item: TaskListEntity) {
            name.text = item.name
            itemView.setOnClickListener { onClick(item) }
            delete.setOnClickListener { onDelete(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(LayoutInflater.from(parent.context).inflate(R.layout.item_task_list, parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) =
        holder.bind(getItem(position))
}

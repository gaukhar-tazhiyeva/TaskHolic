package com.example.taskholic.ui.tasklists

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taskholic.R
import com.example.taskholic.data.local.entity.TaskListEntity

class TaskListsAdapter(
    private val onItemClick: (TaskListEntity) -> Unit,
    private val onItemOptionsClick: (TaskListEntity, View) -> Unit
) : RecyclerView.Adapter<TaskListsAdapter.ViewHolder>() {

    private val items = mutableListOf<TaskListEntity>()

    fun submitList(newItems: List<TaskListEntity>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task_list_card, parent, false)
        return ViewHolder(view, onItemClick, onItemOptionsClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(
        itemView: View,
        private val onItemClick: (TaskListEntity) -> Unit,
        private val onItemOptionsClick: (TaskListEntity, View) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val tvTitle: TextView = itemView.findViewById(R.id.tvListName)
        private val btnOptions: ImageButton = itemView.findViewById(R.id.btnOptions)

        fun bind(item: TaskListEntity) {
            tvTitle.text = item.name

            itemView.setOnClickListener {
                onItemClick(item)
            }

            btnOptions.setOnClickListener {
                onItemOptionsClick(item, it)
            }
        }
    }
}

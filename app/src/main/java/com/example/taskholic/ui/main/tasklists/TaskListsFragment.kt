package com.example.taskholic.ui.tasklists

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.taskholic.R
import com.example.taskholic.data.local.entity.TaskListEntity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskListsFragment : Fragment(R.layout.fragment_task_lists) {

    private val vm: TaskListsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val et = view.findViewById<EditText>(R.id.etNewList)
        val btn = view.findViewById<Button>(R.id.btnAddList)
        val rv = view.findViewById<RecyclerView>(R.id.rvLists)

        val adapter = TaskListAdapter(
            onClick = { openTasks(it) },
            onDelete = { vm.deleteList(it) }
        )
        rv.adapter = adapter

        vm.allLists.observe(viewLifecycleOwner) { adapter.submitList(it) }

        btn.setOnClickListener {
            val name = et.text.toString()
            if (name.isNotBlank()) {
                vm.addList(name)
                et.text = null
            }
        }
    }

    private fun openTasks(list: TaskListEntity) {
        val action = TaskListsFragmentDirections
            .actionListsToTasks(listId = list.listId, listName = list.name)
        findNavController().navigate(action)
    }
}

package com.example.taskholic.ui.tasks

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.taskholic.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TasksFragment : Fragment(R.layout.fragment_tasks) {

    private val args: TasksFragmentArgs by navArgs()
    private val vm: TasksViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireActivity().title = args.listName

        val et = view.findViewById<EditText>(R.id.etNewTask)
        val btn = view.findViewById<Button>(R.id.btnAddTask)
        val rv = view.findViewById<RecyclerView>(R.id.rvTasks)

        val adapter = TaskAdapter(
            onToggle = { task, checked -> vm.toggleCompleted(task, checked) },
            onDelete = { task -> vm.deleteTask(task) }
        )
        rv.adapter = adapter

        vm.tasks.observe(viewLifecycleOwner) { adapter.submitList(it) }

        btn.setOnClickListener {
            val title = et.text.toString()
            if (title.isNotBlank()) {
                vm.addTask(title)
                et.text = null
            }
        }
    }
}

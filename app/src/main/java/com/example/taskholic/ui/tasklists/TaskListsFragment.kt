package com.example.taskholic.ui.tasklists

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskholic.R
import com.example.taskholic.data.local.entity.TaskListEntity
import kotlinx.coroutines.launch

class TaskListsFragment : Fragment(R.layout.fragment_task_lists) {

    private val viewModel: TaskListsViewModel by viewModels()
    private lateinit var adapter: TaskListsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rvTaskLists)
        val emptyState = view.findViewById<View>(R.id.tvEmptyState)
        val fab = view.findViewById<View>(R.id.fabAddTaskList)
        val btnBack = view.findViewById<ImageView>(R.id.btnBack) // кнопка назад

        // Обработка нажатия на кнопку назад
        btnBack.setOnClickListener {
            findNavController().popBackStack() // возвращаемся к предыдущему фрагменту
        }

        val layoutManager = LinearLayoutManager(requireContext()).apply {
            orientation = LinearLayoutManager.VERTICAL
            reverseLayout = false
            stackFromEnd = false
        }

        recyclerView.layoutManager = layoutManager

        adapter = TaskListsAdapter(
            onItemClick = { taskList ->
                val direction = TaskListsFragmentDirections
                    .actionTaskListsFragmentToTasksFragment(taskList.listId)
                findNavController().navigate(direction)
            },
            onItemOptionsClick = { taskList, view ->
                showTaskListOptions(taskList, view)
            }
        )

        recyclerView.adapter = adapter

        fab.setOnClickListener {
            AddTaskListDialogFragment { name ->
                viewModel.addTaskList(name) { listId ->
                    val direction =
                        TaskListsFragmentDirections
                            .actionTaskListsFragmentToTasksFragment(listId)
                    findNavController().navigate(direction)
                }
            }.show(parentFragmentManager, "addTaskList")
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                if (state.lists.isEmpty()) {
                    recyclerView.visibility = View.GONE
                    emptyState.visibility = View.VISIBLE
                } else {
                    recyclerView.visibility = View.VISIBLE
                    emptyState.visibility = View.GONE
                    // новые элементы идут сверху
                    adapter.submitList(state.lists.reversed())
                }
            }
        }
    }

    private fun showTaskListOptions(taskList: TaskListEntity, anchor: View) {
        val popup = PopupMenu(requireContext(), anchor)
        popup.menu.add("Edit")
        popup.menu.add("Delete")

        popup.setOnMenuItemClickListener { item ->
            when (item.title) {
                "Edit" -> {
                    val dialog = AddTaskListDialogFragment { newName ->
                        viewModel.updateTaskList(taskList, newName)
                    }
                    dialog.show(parentFragmentManager, "editTaskList")
                }
                "Delete" -> {
                    viewModel.deleteTaskList(taskList)
                }
            }
            true
        }

        popup.show()
    }
}

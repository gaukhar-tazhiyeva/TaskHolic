package com.example.taskholic.ui.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskholic.R
import com.example.taskholic.data.local.entity.TaskEntity
import com.example.taskholic.databinding.FragmentTasksBinding
import com.example.taskholic.data.local.database.AppDatabase
import com.example.taskholic.data.remote.network.NetworkModule
import com.example.taskholic.data.remote.repository.TaskRepository
import com.example.taskholic.data.repository.TaskListRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import androidx.navigation.fragment.findNavController


class TasksFragment : Fragment() {

    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!

    private val args: TasksFragmentArgs by navArgs()

    private val database by lazy { AppDatabase.getInstance(requireContext()) }
    private val taskRepository by lazy { TaskRepository(database.taskDao(), database.taskListDao(), NetworkModule.firebaseApi) }
    private val taskListsRepository by lazy { TaskListRepository(database.taskListDao()) }

    private val viewModel: TasksViewModel by viewModels {
        TasksViewModelFactory(taskRepository, taskListsRepository)
    }

    private lateinit var adapter: TasksAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycler()
        observeState()

        // Кнопка "назад"
        val btnBack = binding.btnBack
        btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.load(args.taskListId)

        binding.fabAddTask.setOnClickListener {
            AddTaskDialogFragment { title ->
                viewModel.addTask(args.taskListId, title)
            }.show(parentFragmentManager, "addTask")
        }
    }

    private fun showTaskOptions(task: TaskEntity, anchor: View) {
        val popup = PopupMenu(requireContext(), anchor)
        popup.menu.add("Edit")
        popup.menu.add("Delete")
        popup.setOnMenuItemClickListener { item ->
            when (item.title) {
                "Edit" -> {
                    val editDialog = AddTaskDialogFragment { newTitle ->
                        viewModel.updateTaskTitle(task, newTitle)
                    }
                    editDialog.show(parentFragmentManager, "editTask")
                }
                "Delete" -> viewModel.deleteTask(task)
            }
            true
        }
        popup.show()
    }

    private fun setupRecycler() {
        adapter = TasksAdapter(
            onTaskClick = { task -> viewModel.toggleTask(task) },
            onTaskLongClick = { task, view -> showTaskOptions(task, view) }
        )

        binding.rvTasks.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTasks.adapter = adapter
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collectLatest { state ->
                adapter.submitList(state.tasks)
                binding.tvListTitle.text = state.listName
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

package com.example.taskholic.ui.tasklists

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment

class AddTaskListDialogFragment(
    private val onCreateClick: (String) -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val input = EditText(requireContext()).apply {
            hint = "Task List name"
        }

        return AlertDialog.Builder(requireContext())
            .setTitle("New Task List")
            .setView(input)
            .setPositiveButton("Add") { _, _ ->
                val name = input.text.toString()
                if (name.isNotBlank()) {
                    onCreateClick(name)
                }
            }
            .setNegativeButton("Cancel", null)
            .create()
    }
}

package com.example.taskholic.ui.tasks

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.fragment.app.DialogFragment

class AddTaskDialogFragment(
    private val onTaskAdded: (String) -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val input = EditText(requireContext())
        input.hint = "Task name"
        input.inputType = InputType.TYPE_CLASS_TEXT

        builder.setTitle("New Task")
            .setView(input)
            .setPositiveButton("Add") { _, _ ->
                val title = input.text.toString().trim()
                if (title.isNotEmpty()) {
                    onTaskAdded(title)
                }
            }
            .setNegativeButton("Cancel", null)

        return builder.create()
    }
}


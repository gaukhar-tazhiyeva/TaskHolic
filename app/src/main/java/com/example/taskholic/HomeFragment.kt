package com.example.taskholic

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnPomodoro = view.findViewById<LinearLayout>(R.id.btn_open_pomodoro)
        btnPomodoro.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_pomodoro)
        }

        val btnTaskLists = view.findViewById<LinearLayout>(R.id.btn_open_tasklists)
        btnTaskLists.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_taskLists)
        }

        val btnQuote = view.findViewById<LinearLayout>(R.id.btn_open_quote)
        btnQuote.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_quote)
        }


        val btnExit = view.findViewById<LinearLayout>(R.id.btn_exit)
        btnExit.setOnClickListener {
            // Закрываем активность
            requireActivity().finish()
            // Опционально завершить процесс приложения
            // System.exit(0)
        }
    }
}

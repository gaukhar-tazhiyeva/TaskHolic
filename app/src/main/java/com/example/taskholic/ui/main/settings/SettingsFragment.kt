package com.example.taskholic.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.taskholic.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.focusMinutes.observe(viewLifecycleOwner) { minutes ->
            binding.etFocusMinutes.setText(minutes.toString())
        }

        viewModel.breakMinutes.observe(viewLifecycleOwner) { minutes ->
            binding.etBreakMinutes.setText(minutes.toString())
        }

        viewModel.theme.observe(viewLifecycleOwner) { theme ->
            val rbToCheck: RadioButton = when (theme) {
                "light" -> binding.rbLight
                "dark" -> binding.rbDark
                else -> binding.rbSystem
            }
            rbToCheck.isChecked = true
        }

        binding.btnSave.setOnClickListener {
            val focus = binding.etFocusMinutes.text.toString().toIntOrNull() ?: 25
            val brk = binding.etBreakMinutes.text.toString().toIntOrNull() ?: 5

            viewModel.saveFocusMinutes(focus)
            viewModel.saveBreakMinutes(brk)

            val selectedTheme = when (binding.rgTheme.checkedRadioButtonId) {
                binding.rbLight.id -> "light"
                binding.rbDark.id -> "dark"
                else -> "system"
            }
            viewModel.saveTheme(selectedTheme)
        }

        viewModel.loadSettings()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

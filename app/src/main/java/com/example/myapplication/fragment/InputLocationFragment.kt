package com.example.myapplication.fragment

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.activity.ContentActivity
import com.example.myapplication.databinding.FragmentInputLocationBinding
import com.example.myapplication.databinding.FragmentLoginConfirmationBinding
import com.example.myapplication.ui.view_model.InputLocationViewModel

class InputLocationFragment : Fragment() {

    private var _binding: FragmentInputLocationBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInputLocationBinding.inflate(inflater, container, false)
        binding.confirmButton.setOnClickListener {
            findNavController().navigate(R.id.action_inputLocationFragment_to_homeFragment)
        }
        return binding.root
    }
}
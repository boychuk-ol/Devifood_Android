package com.example.myapplication.fragment

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.activity.ContentActivity
import com.example.myapplication.databinding.FragmentCartBinding
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.databinding.FragmentLoginConfirmationBinding
import com.example.myapplication.ui.view_model.LoginConfirmationViewModel

class LoginConfirmationFragment : Fragment() {

    private var _binding: FragmentLoginConfirmationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginConfirmationBinding.inflate(inflater, container, false)
        binding.confirmButton.setOnClickListener {
            val intentToContentActivity: Intent = Intent(requireContext(), ContentActivity::class.java)
            startActivity(intentToContentActivity)
        }
        return binding.root
    }
}
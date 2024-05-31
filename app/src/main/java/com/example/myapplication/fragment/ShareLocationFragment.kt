package com.example.myapplication.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentShareLocationBinding

class ShareLocationFragment : Fragment() {

    private var _binding: FragmentShareLocationBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShareLocationBinding.inflate(inflater, container, false)

        binding.inputLocation.setOnClickListener {
            findNavController().navigate(R.id.action_shareLocationFragment_to_sharePhoneFragment)
        }

        return binding.root
    }

}
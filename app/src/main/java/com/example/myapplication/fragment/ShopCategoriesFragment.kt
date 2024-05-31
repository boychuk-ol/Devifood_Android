package com.example.myapplication.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.databinding.FragmentShopCategoriesBinding

class ShopCategoriesFragment : Fragment() {

    private var _binding: FragmentShopCategoriesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShopCategoriesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val categories = arrayListOf(
//            Category(1, "moloko", Image(1, "mo", "kfc1", ".png",  null)),
//            Category(2, "meat", Image(2, "me", "kfc2", ".png",  null)),
//            Category(3, "fruits", Image(3, "fr", "kfc3", ".png",  null)),
//            Category(4, "vegetables", Image(4, "ve", "kfc4", ".png",  null))
//        )
//
//        val adapter = CategoriesAdapter(requireContext(), categories)
//        binding.categoryGrid.adapter = adapter

    }
}
package com.example.myapplication.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapter.CategoriesAdapter
import com.example.myapplication.databinding.FragmentShopCategoriesBinding
import com.example.myapplication.model.Category
import com.example.myapplication.model.Shop
import com.example.myapplication.service.CategoryService
import com.example.myapplication.service.ShopService
import kotlinx.coroutines.launch

class ShopCategoriesFragment : Fragment() {

    private var _binding: FragmentShopCategoriesBinding? = null
    private val binding get() = _binding!!
    private val shopService: ShopService = ShopService()
    private val categoryService: CategoryService = CategoryService()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShopCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.categoryGrid.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.categoryGrid.isClickable = true
        val shop = arguments?.getParcelable<Shop>("shop")
        viewLifecycleOwner.lifecycleScope.launch {
            if (shop != null) {
                val bundle: Bundle = Bundle()
                bundle.putParcelable("shop", shop)
                val adapter = CategoriesAdapter(ArrayList(), bundle)
                val categories = categoryService.getCategoriesByShop(shop.shopID)
                binding.categoryGrid.adapter = adapter
                Log.d("WEQ", categories?.size.toString())
                adapter.setCategories(categories)
            }
        }

//
//        val adapter = CategoriesAdapter(requireContext(), categories)
//        binding.categoryGrid.adapter = adapter

    }
}
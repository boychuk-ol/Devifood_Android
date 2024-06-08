package com.example.myapplication.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
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

        val shop = arguments?.getParcelable<Shop>("shop")
        viewLifecycleOwner.lifecycleScope.launch {
            if (shop != null) {
                val categories = categoryService.getCategoriesByShop(shop.shopID)
                val adapter = categories?.let { ArrayList<Category>(it) }?.let {
                    CategoriesAdapter(requireContext(),
                        it
                    )
                }
                binding.categoryGrid.adapter = adapter
            }
        }

//
//        val adapter = CategoriesAdapter(requireContext(), categories)
//        binding.categoryGrid.adapter = adapter

    }
}
package com.example.myapplication.fragment

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapter.ShopsAdapter
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.databinding.FragmentShopsBinding
import com.example.myapplication.model.Category
import com.example.myapplication.model.Shop
import com.example.myapplication.service.ShopService
import com.example.myapplication.ui.view_model.ShopsViewModel
import kotlinx.coroutines.launch

class ShopsFragment : Fragment() {

    private var _binding: FragmentShopsBinding? = null
    private val binding get() = _binding!!
    private val shopService: ShopService = ShopService()
    private val shopsAdapter: ShopsAdapter = ShopsAdapter(ArrayList(), R.layout.shop_item_full_width)
    companion object {
        fun newInstance() = ShopsFragment()
    }

    private val viewModel: ShopsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShopsBinding.inflate(inflater, container, false)
        binding.categoryShopsRecycler.apply {
            adapter = shopsAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            isClickable = true
        }
        val category: Category? = arguments?.getParcelable<Category>("category")
        if(category != null) {
            binding.shopsCategory.text = category.name
        } else {
            binding.categoryShopsRecycler.visibility = View.GONE
            binding.shopsCategory.text = "No shops found"
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val category: Category? = arguments?.getParcelable<Category>("category")
        viewLifecycleOwner.lifecycleScope.launch {
            if (category != null) {
                val shops: ArrayList<Shop>? = shopService.getShopsByCategory(category.categoryID)
                shops?.let { shopsAdapter.setShops(it) }
            }
        }
    }
}
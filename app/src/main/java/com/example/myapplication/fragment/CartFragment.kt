package com.example.myapplication.fragment

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapter.ProductsAdapter
import com.example.myapplication.databinding.FragmentCartBinding
import com.example.myapplication.databinding.FragmentShopAssortmentBinding
import com.example.myapplication.model.Product
import com.example.myapplication.ui.view_model.CartViewModel

class CartFragment : Fragment() {


    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var productsAdapter: ProductsAdapter
    private val viewModel: CartViewModel by activityViewModels()

    companion object {
        fun newInstance() = CartFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)

        productsAdapter = ProductsAdapter(ArrayList(), viewModel)

        Log.d("TESTSET", viewModel.cartState.value?.products?.size.toString())

        val productsSet: Set<Product> = viewModel.cartState.value?.products?.toHashSet() ?: emptySet()
        productsAdapter.setProducts(ArrayList(productsSet))

        Log.d("TESTSET", productsSet.size.toString())

        binding.cartList.apply {
            adapter = productsAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            isClickable = true
        }

        return binding.root
    }
}
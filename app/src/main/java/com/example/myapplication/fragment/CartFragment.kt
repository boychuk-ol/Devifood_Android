package com.example.myapplication.fragment

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
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
        viewModel.setDeleteButtonVisibility(true)

        productsAdapter = ProductsAdapter(ArrayList(), viewModel, viewLifecycleOwner, requireContext())

//        val productsSet: Set<Product> = viewModel.cartState.value?.products?.toHashSet() ?: emptySet()
//        productsAdapter.setProducts(ArrayList(productsSet))
        val productsSet: Set<Product> = viewModel.cartState.value?.products?.toHashSet() ?: emptySet()
        productsAdapter.setProducts(ArrayList(productsSet))


        binding.cartList.apply {
            adapter = productsAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            isClickable = true
        }

        binding.makeOrderButton.setOnClickListener {
            val bundle: Bundle = Bundle()
            bundle.putParcelable("order", viewModel.cartState.value)
            findNavController().navigate(R.id.action_cartFragment_to_orderProcessingFragment, bundle)
        }

        return binding.root
    }
}
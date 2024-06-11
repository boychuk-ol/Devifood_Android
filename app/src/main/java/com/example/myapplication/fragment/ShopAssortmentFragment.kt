package com.example.myapplication.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapter.ProductsAdapter
import com.example.myapplication.databinding.FragmentShopAssortmentBinding
import com.example.myapplication.model.Category
import com.example.myapplication.model.Shop
import com.example.myapplication.service.ProductService
import com.example.myapplication.ui.view_model.CartViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class ShopAssortmentFragment : Fragment() {

    private var _binding: FragmentShopAssortmentBinding? = null
    private val binding get() = _binding!!
    private val cartViewModel: CartViewModel by activityViewModels()
    private lateinit var productAdapter: ProductsAdapter
    private val productService: ProductService = ProductService()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShopAssortmentBinding.inflate(inflater, container, false)

        binding.productsLayout.minimumHeight = resources.displayMetrics.heightPixels - binding.shopInfoLayout.height - 65

        productAdapter =  ProductsAdapter(ArrayList(), cartViewModel, viewLifecycleOwner, requireContext())
        binding.productsList.apply {
            adapter = productAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            isClickable = false
        }

        cartViewModel.setDeleteButtonVisibility(false)

        val category = arguments?.getParcelable<Category>("category")
        if(category != null) {
            viewLifecycleOwner.lifecycleScope.launch {
                val products = productService.getProductsByCategory(category.categoryID)
                Log.d("categoryID", category.categoryID.toString())
                Log.d("TESTIK", products?.size.toString())
                products?.let { productAdapter.setProducts(it) }
            }
        }
        val shop = arguments?.getParcelable<Shop>("shop")
        if(shop != null) {
            try {
                val imageUrl = "http://192.168.1.136/devifood/shopImages/${shop.image.name + shop.image.extension}"
                Picasso.get()
                    .load(imageUrl)
                    .error(R.drawable.google_icon_background)
                    .into(binding.backgroundImg, object : com.squareup.picasso.Callback {
                        override fun onSuccess() {
                            Log.d("Picasso", "Image loaded successfully")
                        }
                        override fun onError(e: Exception?) {
                            Log.e("Picasso2", "Error loading image", e)
                        }
                    })
            } catch (e: Exception) {
                Log.d("IMAGE_ERROR", e.message.toString())
            }
        }

        return binding.root
    }

//    private fun addCardView(parentLayout: LinearLayout, imageResId: Int) {
//        // Create a new CardView
//        val cardView = CardView(requireContext()).apply {
//            layoutParams = LinearLayout.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                120.dpToPx()
//            ).apply {
//                setMargins(0, 10.dpToPx(), 0, 0)
//            }
//            radius = 8f
//            setCardBackgroundColor(Color.WHITE)
//            cardElevation = 8f
//        }
//
//        // Create a new LinearLayout
//        val linearLayout = LinearLayout(requireContext()).apply {
//            layoutParams = LinearLayout.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT
//            )
//            orientation = LinearLayout.HORIZONTAL
//        }
//
//        // Create a new ImageView
//        val imageView = ImageView(requireContext()).apply {
//            layoutParams = LinearLayout.LayoutParams(
//                150.dpToPx(),
//                ViewGroup.LayoutParams.MATCH_PARENT
//            ).apply {
//                setMargins(15.dpToPx(), 10.dpToPx(), 0, 10.dpToPx())
//            }
//            setImageResource(imageResId)
//        }
//
//        // Add ImageView to LinearLayout
//        linearLayout.addView(imageView)
//
//        // Add LinearLayout to CardView
//        cardView.addView(linearLayout)
//
//        // Add CardView to parent LinearLayout
//        parentLayout.addView(cardView)
//    }
//    private fun Int.dpToPx(): Int {
//        return (this * resources.displayMetrics.density).toInt()
//    }

}
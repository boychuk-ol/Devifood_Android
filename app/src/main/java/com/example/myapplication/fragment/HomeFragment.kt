package com.example.myapplication.fragment

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.compose.ui.text.capitalize
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.adapter.ShopsAdapter
import com.example.myapplication.model.Category
import com.example.myapplication.model.Shop
import com.example.myapplication.service.CategoryService
import com.example.myapplication.service.ImageService
import com.example.myapplication.service.ShopService
import kotlinx.coroutines.launch
import java.util.Locale


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val shopService: ShopService = ShopService()
    private val categoryService: CategoryService = CategoryService()
    private val adapters = listOf(
        ShopsAdapter(ArrayList(), R.layout.shop_item),
        ShopsAdapter(ArrayList(), R.layout.shop_item),
        ShopsAdapter(ArrayList(), R.layout.shop_item),
        ShopsAdapter(ArrayList(), R.layout.shop_item),
        ShopsAdapter(ArrayList(), R.layout.shop_item)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.frameLayout2.minHeight = resources.displayMetrics.heightPixels
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val shopCategories = categoryService.getCategoriesByEntity("shop")
                val set = ConstraintSet()
                var previousLayoutId = binding.frameLayout2.id

                if (shopCategories != null) {
                    for ((index, category) in shopCategories.withIndex()) {
                        val shopsByCategoryLayout = createShopsCategoryLayout(
                            category,
                            adapters[index]
                        )

                        // Add the layout first before setting constraints
                        binding.frameLayout2.addView(shopsByCategoryLayout)
                        set.clone(binding.frameLayout2)

                        if (previousLayoutId == binding.frameLayout2.id) {
                            set.connect(shopsByCategoryLayout.id, ConstraintSet.TOP, binding.frameLayout2.id, ConstraintSet.TOP, 10)
                        } else {
                            set.connect(shopsByCategoryLayout.id, ConstraintSet.TOP, previousLayoutId, ConstraintSet.BOTTOM, 10)
                        }

                        set.applyTo(binding.frameLayout2)
                        previousLayoutId = shopsByCategoryLayout.id

                        val shops = shopService.getShopsByCategory(category.categoryID)
                        if (shops == null) {
                            shopsByCategoryLayout.visibility = View.GONE
                        } else {
                            adapters[index].setShops(shops)
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun createShopsCategoryLayout(category: Category, shopAdapter: ShopsAdapter): LinearLayout {
        val context = this

        val linearLayout = LinearLayout(requireContext()).apply {
            id = View.generateViewId()
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            orientation = LinearLayout.VERTICAL
            setPadding(0, 10, 0, 10)
        }

        val textView = TextView(requireContext()).apply {
            id = View.generateViewId()
            isClickable = true
            textSize = 18F
            setTypeface(null, Typeface.BOLD)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(35, 20, 10, 0)
            }
            text = category.name.capitalize(Locale.ROOT)
            setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            setOnClickListener {
                val bundle = Bundle()
                bundle.putParcelable("category", category)
                findNavController().navigate(R.id.action_homeFragment_to_shopsFragment, bundle)
            }
        }

        val recyclerView = RecyclerView(requireContext()).apply {
            id = View.generateViewId()
            adapter = shopAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            isClickable = true
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(10, 15, 10, 0)
            }
        }

        linearLayout.addView(textView)
        linearLayout.addView(recyclerView)

        return linearLayout
    }


}

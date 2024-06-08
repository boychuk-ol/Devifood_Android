package com.example.myapplication.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.adapter.ShopsAdapter
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
    private val adapter = ShopsAdapter(ArrayList())
    private val adapter2 = ShopsAdapter(ArrayList())
    private val adapter3 = ShopsAdapter(ArrayList())
    private val adapter4 = ShopsAdapter(ArrayList())
    private val adapter5 = ShopsAdapter(ArrayList())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fastFoodRecyclerView.adapter = adapter
        binding.fastFoodRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.fastFoodRecyclerView.isClickable = true

        binding.supermarketRecyclerView.adapter = adapter2
        binding.supermarketRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.supermarketRecyclerView.isClickable = true

        binding.asianRecyclerView.adapter = adapter3
        binding.asianRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.asianRecyclerView.isClickable = true

//        binding.shopList4.adapter = adapter4
//        binding.shopList4.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//        binding.shopList4.isClickable = true
//
//        binding.shopList5.adapter = adapter5
//        binding.shopList5.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//        binding.shopList5.isClickable = true

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val shopCategories = categoryService.getCategoriesByEntity("shop")
                var previousViewId: Int? = null
//                if(shopCategories != null) {
//                    for (shopCategory in shopCategories) {
//                        val titleView = createTextView(shopCategory.name.replaceFirstChar {
//                            if (it.isLowerCase()) it.titlecase(
//                                Locale.getDefault()
//                            ) else it.toString()
//                        })
//                        titleView.id = View.generateViewId()
//                        binding.frameLayout2.addView(titleView)
//
//                        val shops = shopService.getShopsByCategory(shopCategory.name)
//                        val recyclerView = createRecyclerView(shops)
//                        recyclerView.id = View.generateViewId()
//                        binding.frameLayout2.addView(recyclerView)
//
//                        val set = ConstraintSet()
//                        set.clone(binding.frameLayout2)
//
//                        if (previousViewId == null) {
//                            set.connect(titleView.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 35)
//                        } else {
//                            set.connect(titleView.id, ConstraintSet.TOP, previousViewId, ConstraintSet.BOTTOM, 20)
//                        }
//
//                        set.connect(titleView.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 10)
//                        set.connect(titleView.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 10)
//
//                        set.connect(recyclerView.id, ConstraintSet.TOP, titleView.id, ConstraintSet.BOTTOM, 20)
//                        set.connect(recyclerView.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
//                        set.connect(recyclerView.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
//                        set.applyTo(binding.frameLayout2)
//
//                        previousViewId = titleView.id
//                    }
//                }
                val shops1 = shopCategories!![0].let { shopService.getShopsByCategory(it.name.capitalize()) }
                val shops2 = shopCategories[1].let { shopService.getShopsByCategory(it.name.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.ROOT
                    ) else it.toString()
                }) }
                val shops3 = shopCategories[2].let { shopService.getShopsByCategory(it.name.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.ROOT
                    ) else it.toString()
                }) }
                val shops4 = shopCategories[3].let { shopService.getShopsByCategory(it.name.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.ROOT
                    ) else it.toString()
                }) }
                shops1?.let { adapter.setShops(it) }
                shops2?.let { adapter2.setShops(it) }
                shops3?.let { adapter3.setShops(it) }
                shops4?.let { adapter4.setShops(it) }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            try {
                getImages()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun createTextView(text: String): TextView {
        return TextView(requireContext()).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            this.text = text
            textSize = 18f // Размер текста, можно изменить по необходимости
            setTextColor(Color.BLACK) // Цвет текста, можно изменить по необходимости
        }
    }

    fun createRecyclerView(shops: ArrayList<Shop>?): RecyclerView {
        return RecyclerView(requireContext()).apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            // Установите адаптер для RecyclerView здесь
            adapter = ShopsAdapter(shops!!)
        }
    }

    // Функция для преобразования dp в px
    fun Int.dpToPx(): Int {
        return (this * resources.displayMetrics.density).toInt()
    }

    suspend fun getImages() {
        val imageService: ImageService = ImageService()
        val image = imageService.getImageById(2)
        Log.d("IMAGE_TEST1", image?.imageID.toString())
        Log.d("IMAGE_TEST2", image?.name.toString())
        Log.d("IMAGE_TEST3", image?.entityName.toString())
        Log.d("IMAGE_TEST4", image?.extension.toString())
        Log.d("IMAGE_TEST5", image?.fullLink.toString())

    }

}

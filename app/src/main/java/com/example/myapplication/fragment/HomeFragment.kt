package com.example.myapplication.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.adapter.ShopsAdapter
import com.example.myapplication.service.ImageService
import com.example.myapplication.service.ShopService
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val shopService: ShopService = ShopService()
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

        binding.shopList1.adapter = adapter
        binding.shopList1.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.shopList1.isClickable = true

        binding.shopList2.adapter = adapter2
        binding.shopList2.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.shopList2.isClickable = true

        binding.shopList3.adapter = adapter3
        binding.shopList3.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.shopList3.isClickable = true

        binding.shopList4.adapter = adapter4
        binding.shopList4.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.shopList4.isClickable = true

        binding.shopList5.adapter = adapter5
        binding.shopList5.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.shopList5.isClickable = true

        shopService.getShops(adapter)
        shopService.getShopById(1, adapter2)
        shopService.getShopsByCategory("fast-food", adapter3)
        shopService.getShopByName("atb", adapter4)

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                getImages()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun getImages() {
        val imageService: ImageService = ImageService()
        val image = imageService.getImageById(2)
        Log.d("IMAGE_TEST1", image?.imageID.toString())
        Log.d("IMAGE_TEST2", image?.name.toString())
        Log.d("IMAGE_TEST3", image?.entityName.toString())
        Log.d("IMAGE_TEST4", image?.extension .toString())
        Log.d("IMAGE_TEST5", image?.fullLink.toString())

    }



}
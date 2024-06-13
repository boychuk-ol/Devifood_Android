package com.example.myapplication.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.activity.ContentActivity
import com.example.myapplication.model.Image
import com.example.myapplication.model.Shop
import com.example.myapplication.databinding.FragmentSharePhoneBinding
import com.example.myapplication.model.Category
import com.example.myapplication.ui.view_model.ClientViewModel


class SharePhoneFragment : Fragment() {

    private var _binding: FragmentSharePhoneBinding? = null
    private val binding get() = _binding!!
    private val clientViewModel: ClientViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSharePhoneBinding.inflate(inflater, container, false)
        binding.codeSender.setOnClickListener {
            findNavController().navigate(R.id.action_sharePhoneFragment_to_loginConfirmationFragment)
        }

        return binding.root

    }

//    private fun testt() {
//        val retrofitService = RetrofitClient.getApiClient()?.create(RetrofitAPI::class.java)
//        retrofitService!!.test().enqueue(object: Callback<String> {
//
//            override fun onResponse(call: Call<String>, response: Response<String>) {
//                if(response.isSuccessful) {
//                    Log.d("TEST11", response.body().toString());
//                }
//                else {
//                    Log.d("ERROR_RESPONSE", response.message())
//                    Log.d("FAIL", "FAIL")
//                }
//            }
//
//            override fun onFailure(call: Call<String>, t: Throwable) {
//                Log.d("FAIL2", t.message.toString())
//            }
//        })
//    }
//
//    private fun addShop(shop: Shop) {
//        val retrofitService = RetrofitClient.getApiClient()?.create(RetrofitAPI::class.java)
//        retrofitService!!.createShop(shop).enqueue(object: Callback<Shop> {
//            override fun onResponse(call: Call<Shop>, response: Response<Shop>) {
//                if(response.isSuccessful) {
//                    Log.d("TEST11", shop.shopName.toString());
//                    Log.d("TEST2", shop.category.toString());
//                    Log.d("TEST3", shop.rating.toString());
//                    Log.d("TEST4", shop.reviews.toString());
//                }
//                else {
//                    Log.d("ERROR_RESPONSE", response.message())
//                    Log.d("FAIL", "FAIL")
//                }
//            }
//
//            override fun onFailure(call: Call<Shop>, t: Throwable) {
//                Log.d("FAIL2", t.message.toString())
//            }
//        })
//    }
}


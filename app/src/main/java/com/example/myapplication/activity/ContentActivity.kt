package com.example.myapplication.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityContentBinding
import com.example.myapplication.fragment.CartFragment
import com.example.myapplication.model.Cart
import com.example.myapplication.ui.view_model.CartViewModel

class ContentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContentBinding
    private val cartViewModel: CartViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cartButton.visibility = View.INVISIBLE

        cartViewModel.cartState.observe(this, Observer { cartState ->
            if (cartState.products.size > 0) {
                binding.cartButton.visibility = View.VISIBLE
                val totalPrice = cartState.products.sumOf { it.actualPrice.toDouble() }.toFloat() ?: 0.0
                binding.cartButton.text = "${cartState.products.size} for ${totalPrice}₴"
            } else {
                binding.cartButton.visibility = View.INVISIBLE
            }
        })

        binding.cartButton.setOnClickListener { // assuming you have a NavHostFragment with ID 'nav_host_fragment'
            binding.cartButton.visibility = View.INVISIBLE
            val removeButton = findViewById<ImageButton>(R.id.removeButton)
            removeButton.visibility = View.VISIBLE
            val navController = findNavController(R.id.contentFragment)
            navController.navigate(R.id.action_global_cartFragment)

        }
    }

}
package com.example.myapplication.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.databinding.ActivityShareDataBinding

class ShareDataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShareDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShareDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
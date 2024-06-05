package com.example.myapplication.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.myapplication.R
import com.example.myapplication.model.Category
import com.example.myapplication.databinding.CategoryItemBinding
import com.squareup.picasso.Picasso

class CategoriesAdapter(private val context: Context,
                        private val categories: ArrayList<Category>): BaseAdapter() {

    override fun getCount(): Int = categories.size

    override fun getItem(position: Int): Any = categories[position]

    override fun getItemId(position: Int): Long = categories[position].categoryID.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding: CategoryItemBinding
        val view: View

        if (convertView == null) {
            binding = CategoryItemBinding.inflate(LayoutInflater.from(context), parent, false)
            view = binding.root
            view.tag = binding
        } else {
            view = convertView
            binding = view.tag as CategoryItemBinding
        }

        val category = categories[position].name
        val image = categories[position].image

        binding.shopName.setText(category)
        try {
            Picasso.get()
                .load("http://192.168.1.136/devifood/images/${image?.name + image?.extension}")
                .error(R.drawable.google_icon_background)  // Установите изображение для отображения при ошибке
                .into(binding.shopImage, object : com.squareup.picasso.Callback {
                    override fun onSuccess() {
                        // Логирование успешной загрузки
                        Log.d("Picasso", "Image loaded successfully")
                    }
                    override fun onError(e: Exception?) {
                        // Логирование ошибки
                        Log.e("Picasso2", "Error loading image", e)
                    }
                })
        } catch (e: Exception)
        {
            Log.d("IMAGE_ERROR", e.message.toString())
        }

        return view
    }


}
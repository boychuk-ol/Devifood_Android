package com.example.myapplication.adapter

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.navArgument
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.Category
import com.example.myapplication.service.ShopService
import com.google.android.material.card.MaterialCardView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch


class CategoriesAdapter(private val arrayList: ArrayList<Category>, private val arguments: Bundle): RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val categoryCard: MaterialCardView
        val categoryImage: ImageView
        val categoryName: TextView

        init {
            // Define click listener for the ViewHolder's View
            categoryCard = view.findViewById(R.id.categoryCard2)
            categoryImage = view.findViewById(R.id.categoryImage)
            categoryName = view.findViewById(R.id.categoryName)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.category_item, viewGroup, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val category: Category = arrayList[position]
        viewHolder.categoryName.text = category.name
        val bundle = Bundle()
        bundle.putParcelable("category", category)
        bundle.putParcelable("shop", arguments.getParcelable("shop"))
        viewHolder.itemView.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_shopCategoriesFragment_to_shopAssortmentFragment, bundle)
        )
        val imageUrl = "http://192.168.1.136/devifood/shopImages/${category.image?.name + category.image?.extension}"
        Log.d("ImageURL", "Loading image from URL: $imageUrl")

        try {
            Picasso.get()
                .load(imageUrl)
                .error(R.mipmap.food_categories_foreground)  // Установите изображение для отображения при ошибке
                .into(viewHolder.categoryImage, object : com.squareup.picasso.Callback {
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
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setCategories(categories: List<Category>?) {
        arrayList.clear()
        if (categories != null) {
            arrayList.addAll(categories)
        }
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addCategory(category: Category) {
        arrayList.clear()
        arrayList.add(category)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearCategories() {
        arrayList.clear()
        notifyDataSetChanged()
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = arrayList.size

//    @SuppressLint("ViewHolder")
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//        val inflater: LayoutInflater = LayoutInflater.frCom(context)
//        val view: View = inflater.inflate(R.layout.list_item, parent, false)
//        val listData = getItem(position)
//
//        val shopName = view.findViewById<TextView>(R.id.shop)
//        val category = view.findViewById<TextView>(R.id.category)
//        val rating = view.findViewById<TextView>(R.id.rating)
//        val reviews = view.findViewById<TextView>(R.id.deliveryInfo)
//        val image = view.findViewById<ImageView>(R.id.image)
//
//        shopName.text = listData!!.shopName
//        category.text = listData.category
//        rating.text = listData.rating.toString()
//        reviews.text = listData.reviews.toString()
//        Picasso.get()
//            .load("http://localhost/devifood/images/kfc1.png")
//            .resize(290, 163)
//            .into(image)
//
//        return view
//    }
}
package com.example.myapplication.adapter

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.Shop
import com.example.myapplication.`object`.RetrofitClient.CLOUD_STORAGE_SHOP_IMAGES_URL
import com.squareup.picasso.Picasso

class ShopsAdapter(private val arrayList: ArrayList<Shop>, private val layoutResId: Int) : RecyclerView.Adapter<ShopsAdapter.ViewHolder>() {

    class ViewHolder(view: View, layoutResId: Int) : RecyclerView.ViewHolder(view) {
        val shopName: TextView?
        val category: TextView?
        val rating: TextView?
        val reviews: TextView?
        val image: ImageView?
        val ratingLayout: LinearLayout?

        init {
            when (layoutResId) {
                R.layout.shop_item -> {
                    shopName = view.findViewById(R.id.categoryName)
                    category = view.findViewById(R.id.shopCategory)
                    rating = view.findViewById(R.id.rating)
                    reviews = view.findViewById(R.id.reviews)
                    image = view.findViewById(R.id.categoryImage)
                    ratingLayout = view.findViewById(R.id.lowerLayout)
                }
                R.layout.shop_item_full_width -> {
                    shopName = view.findViewById(R.id.categoryName2)
                    category = view.findViewById(R.id.shopCategory2)
                    rating = view.findViewById(R.id.rating2)
                    reviews = view.findViewById(R.id.reviews2)
                    image = view.findViewById(R.id.categoryImage2)
                    ratingLayout = view.findViewById(R.id.lowerLayout2)
                }
                else -> {
                    shopName = null
                    category = null
                    rating = null
                    reviews = null
                    image = null
                    ratingLayout = null
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
        return ViewHolder(view, layoutResId)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val shop: Shop = arrayList[position]
        holder.shopName?.text = shop.shopName
        holder.category?.text = shop.category.name
        if (shop.rating == null) {
            holder.ratingLayout?.visibility = View.GONE
        } else {
            holder.rating?.text = shop.rating.toString()
        }
        holder.reviews?.text = shop.reviews.toString()

        val bundle = Bundle().apply {
            putParcelable("shop", shop)
        }
        holder.itemView.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_shopCategoriesFragment, bundle)
        )
        holder.itemView.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_shopsFragment_to_shopCategoriesFragment, bundle)
        )

        val imageUrl = "${CLOUD_STORAGE_SHOP_IMAGES_URL}${shop.image.name + shop.image.extension}"
        Log.d("ImageURL", "Loading image from URL: $imageUrl")

        try {
            Picasso.get()
                .load(imageUrl)
                .error(R.drawable.google_icon_background)
                .into(holder.image, object : com.squareup.picasso.Callback {
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

    @SuppressLint("NotifyDataSetChanged")
    fun setShops(shops: ArrayList<Shop>) {
        arrayList.clear()
        arrayList.addAll(shops)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addShop(shop: Shop) {
        arrayList.add(shop)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearShops() {
        arrayList.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
}

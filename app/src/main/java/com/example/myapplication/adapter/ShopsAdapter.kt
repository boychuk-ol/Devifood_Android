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
import com.squareup.picasso.Picasso


class ShopsAdapter(private val arrayList: ArrayList<Shop>): RecyclerView.Adapter<ShopsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val shopName: TextView
        val category: TextView
        val rating: TextView
        val reviews: TextView
        val image: ImageView
        val ratingLayout: LinearLayout

        init {
            // Define click listener for the ViewHolder's View
            shopName = view.findViewById(R.id.shopName)
            category = view.findViewById(R.id.shopCategory)
            rating = view.findViewById(R.id.rating)
            reviews = view.findViewById(R.id.reviews)
            image = view.findViewById(R.id.shopImage)
            ratingLayout = view.findViewById(R.id.lowerLayout)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.shop_item, viewGroup, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val shop: Shop = arrayList[position]
        viewHolder.shopName.text = shop.shopName
        viewHolder.category.text = shop.category.name
        if(shop.rating == null) {
            viewHolder.ratingLayout.visibility = View.GONE
        }
        viewHolder.rating.text = shop.rating.toString()
        viewHolder.reviews.text = shop.reviews.toString()
        val bundle = Bundle()
        bundle.putParcelable("shop", shop)
        viewHolder.itemView.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_shopCategoriesFragment, bundle)
        )
        val imageUrl = "http://192.168.1.136/devifood/shopImages/${shop.image.name + shop.image.extension}"
        Log.d("ImageURL", "Loading image from URL: $imageUrl")

        try {
            Picasso.get()
                .load(imageUrl)
                .error(R.drawable.google_icon_background)  // Установите изображение для отображения при ошибке
                .into(viewHolder.image, object : com.squareup.picasso.Callback {
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
    fun setShops(shops: ArrayList<Shop>) {
        arrayList.clear()
        arrayList.addAll(shops)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addShop(shop: Shop) {
        arrayList.clear()
        arrayList.add(shop)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearShops() {
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
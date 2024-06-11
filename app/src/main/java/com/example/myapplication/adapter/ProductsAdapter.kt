package com.example.myapplication.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.Product
import com.example.myapplication.`object`.RetrofitClient.CLOUD_STORAGE_PRODUCT_IMAGES_URL
import com.example.myapplication.ui.view_model.CartViewModel
import com.squareup.picasso.Picasso


class ProductsAdapter(
    private val arrayList: ArrayList<Product>,
    private val cartViewModel: CartViewModel,
    private val lifecycleOwner: LifecycleOwner,
    private val context: Context
) : RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productName: TextView?
        val productPrice: TextView?
        val productInfo: TextView?
        val productCount: TextView?
        val deleteButton: ImageButton?
        val addButton: ImageButton?
        val removeButton: ImageButton?
        val image: ImageView?

        init {
            productName = view.findViewById(R.id.prodName)
            productPrice = view.findViewById(R.id.prodCost)
            productInfo = view.findViewById(R.id.prodInfo)
            productCount = view.findViewById(R.id.productCount)
            image = view.findViewById(R.id.productImage)
            addButton = view.findViewById(R.id.addButton)
            removeButton = view.findViewById(R.id.removeButton)
            deleteButton = view.findViewById(R.id.deleteButton)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product: Product = arrayList[position]
        holder.productName?.text = product.fullTitle
        holder.productPrice?.text = product.actualPrice.toInt().toString() + "₴"
        holder.productInfo?.text = product.description
        holder.deleteButton?.visibility = View.GONE
        holder.productCount?.visibility = View.GONE
        holder.removeButton?.visibility = View.GONE
        holder.productInfo?.visibility = View.VISIBLE
        holder.productInfo?.maxLines = Integer.MAX_VALUE
        holder.productInfo?.ellipsize = null

        val productCount = cartViewModel.getProductCount(product)
        holder.productCount?.text = "${productCount}x"

        if (productCount > 0) {
            holder.productCount?.visibility = View.VISIBLE
            holder.removeButton?.visibility = View.VISIBLE
            val addButtonLayoutParams = holder.addButton?.layoutParams as ViewGroup.MarginLayoutParams
            val marginPx = (30 * holder.addButton.context.resources.displayMetrics.density).toInt()
            addButtonLayoutParams.setMargins(marginPx, addButtonLayoutParams.topMargin, addButtonLayoutParams.rightMargin, addButtonLayoutParams.bottomMargin)
            holder.addButton.layoutParams = addButtonLayoutParams
        } else {
            holder.productCount?.visibility = View.GONE
            holder.removeButton?.visibility = View.GONE

            val addButtonLayoutParams = holder.addButton?.layoutParams as ViewGroup.MarginLayoutParams
            addButtonLayoutParams.setMargins(0, 0, 0, 0)
            holder.addButton.layoutParams = addButtonLayoutParams
        }

        cartViewModel.isDeleteButtonVisible.observe(lifecycleOwner, Observer { isVisible ->
            holder.deleteButton?.visibility = if (isVisible) View.VISIBLE else View.GONE
        })

        holder.deleteButton?.setOnClickListener {
            deleteProductFromCart(product)
        }


        holder.addButton.setOnClickListener {
            cartViewModel.addProductToCart(product)
            val updatedProductCount = cartViewModel.getProductCount(product)
            holder.productCount?.text = "${updatedProductCount}x"
            if (updatedProductCount == 1 && cartViewModel.isDeleteButtonVisible.value == false) {
                holder.productCount?.visibility = View.VISIBLE
                holder.removeButton?.visibility = View.VISIBLE

                val addButtonLayoutParams =
                    holder.addButton?.layoutParams as ViewGroup.MarginLayoutParams
                val marginPx =
                    (30 * holder.addButton.context.resources.displayMetrics.density).toInt()
                addButtonLayoutParams.setMargins(
                    marginPx,
                    addButtonLayoutParams.topMargin,
                    addButtonLayoutParams.rightMargin,
                    addButtonLayoutParams.bottomMargin
                )
                holder.addButton.layoutParams = addButtonLayoutParams
            }

        }

        holder.removeButton?.setOnClickListener {
            var updatedProductCount = cartViewModel.getProductCount(product)
            holder.productCount?.text = "${updatedProductCount}x"
            if (updatedProductCount > 1) {
                cartViewModel.removeProductFromCart(product)
                updatedProductCount = cartViewModel.getProductCount(product)
                holder.productCount?.text = "${updatedProductCount}x"
            } else if (updatedProductCount == 1 && cartViewModel.isDeleteButtonVisible.value == false) {
                cartViewModel.removeProductFromCart(product)
                holder.productCount?.visibility = View.GONE
                holder.removeButton.visibility = View.GONE
                val addButtonLayoutParams = holder.addButton?.layoutParams as ViewGroup.MarginLayoutParams
                addButtonLayoutParams.setMargins(0, 0, 0, 0)
                holder.addButton.layoutParams = addButtonLayoutParams
            } else if (updatedProductCount == 1 && cartViewModel.isDeleteButtonVisible.value == true) {
                deleteProductFromCart(product)
            }
        }

        holder.productName?.viewTreeObserver?.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                holder.productName?.viewTreeObserver?.removeOnGlobalLayoutListener(this)
                when {
                    holder.productName?.lineCount == 2 -> {
                        holder.productInfo?.maxLines = 3
                        holder.productInfo?.ellipsize = TextUtils.TruncateAt.END
                    }
                    holder.productName?.lineCount ?: 0 > 5 -> {
                        holder.productInfo?.maxLines = 3
                        holder.productInfo?.ellipsize = TextUtils.TruncateAt.END
                    }
                    holder.productName?.lineCount ?: 0 > 2 -> {
                        holder.productInfo?.visibility = View.GONE
                    }
                    holder.productInfo?.lineCount ?: 0 > 5 -> {
                        holder.productInfo?.maxLines = 3
                        holder.productInfo?.ellipsize = TextUtils.TruncateAt.END
                    }
                }
            }
        })

        val imageUrl = "${CLOUD_STORAGE_PRODUCT_IMAGES_URL}${product.image.name + product.image.extension}"
        Log.d("ImageURL", "Loading image from URL: $imageUrl")
        try {
            Picasso.get()
                .load(imageUrl)
                .resize(350, 350)
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
//        holder.shopName?.text = shop.shopName
//        holder.category?.text = shop.category.name
//        if (shop.rating == null) {
//            holder.ratingLayout?.visibility = View.GONE
//        } else {
//            holder.rating?.text = shop.rating.toString()
//        }
//        holder.reviews?.text = shop.reviews.toString()



    }

    fun deleteProductFromCart(product: Product) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setCancelable(true)
        builder.setTitle("Delete")
        builder.setMessage("Are you sure yout want to delete this item?")
        builder.setPositiveButton("Confirm",
            DialogInterface.OnClickListener { dialog, which ->
                cartViewModel.removeProductsFromCart(product)
                val productsSet: Set<Product> = cartViewModel.cartState.value?.products?.toHashSet() ?: emptySet()
                setProducts(ArrayList(productsSet))
            })
        builder.setNegativeButton(android.R.string.cancel,
            DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
            })

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setProducts(products: ArrayList<Product>) {
        arrayList.clear()
        arrayList.addAll(products)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addProduct(product: Product) {
        arrayList.add(product)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearProducts() {
        arrayList.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
}

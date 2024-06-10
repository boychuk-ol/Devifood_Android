package com.example.myapplication.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.Cart
import com.example.myapplication.model.Product

class CartViewModel : ViewModel() {
    private val _cartState = MutableLiveData<Cart>()

    val cartState: LiveData<Cart>
        get() = _cartState

    init {
        _cartState.value = Cart(0, ArrayList())
    }

    fun updateCartState(cart: Cart) {
        _cartState.value = cart
    }

    fun addProductToCart(product: Product) {
        val currentCart = _cartState.value ?: Cart(0, ArrayList())
        currentCart.products.add(product)
        updateCartState(currentCart)
    }

    fun removeProductFromCart(product: Product) {
        val currentCart = _cartState.value ?: Cart(0, ArrayList())
        currentCart.products.remove(product)
        updateCartState(currentCart)
    }

    fun getProductCount(product: Product): Int {
        return _cartState.value?.products?.count { it == product } ?: 0
    }

}
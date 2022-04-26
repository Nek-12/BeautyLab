package com.nek12.beautylab.ui.screens.cart

import com.nek12.beautylab.ui.screens.cart.CartState.*
import com.nek12.flowMVI.android.MVIViewModel

class CartViewModel : MVIViewModel<CartState, CartIntent, CartAction>() {

    override val initialState get() = Loading
    override fun recover(from: Exception) = Error(from)

    override suspend fun reduce(intent: CartIntent): CartState = when (intent) {
        else -> TODO()
    }
}

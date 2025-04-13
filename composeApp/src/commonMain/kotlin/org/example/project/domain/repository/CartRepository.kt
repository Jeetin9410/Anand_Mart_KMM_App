package org.example.project.domain.repository

import com.example.project.Cart
import com.example.project.CartDisplay

interface CartRepository {
    suspend fun insertIntoCart(cart: Cart)
    suspend fun updateCartQuantity(skuId: Long, sessionId: String, quantity: Int)
    suspend fun removeFromCart(skuId: Int, sessionId: String)
    suspend fun clearCart(sessionId: String)
    suspend fun checkIfExistsInCart(skuId: Int, sessionId: String): Boolean
    suspend fun getCartItemBySkuId(skuId: Int, sessionId: String): CartDisplay?
    suspend fun selectAllCartDisplay(): List<CartDisplay>

}
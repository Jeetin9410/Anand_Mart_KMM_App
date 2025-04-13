package org.example.project.data.repository

import com.example.project.AnandMartDb
import com.example.project.Cart
import com.example.project.CartDisplay
import org.example.project.domain.repository.CartRepository

class CartRepositoryImpl(private val db: AnandMartDb) : CartRepository {
    override suspend fun insertIntoCart(cart: Cart) {
        db.cartQueries.insertIntoCart(skuId = cart.skuId, sessionId = cart.sessionId, quantity = cart.quantity)
    }

    override suspend fun updateCartQuantity(skuId: Long, sessionId: String, quantity: Int) {
        db.cartQueries.updateCartQuantity(skuId = skuId, sessionId = sessionId, quantity = quantity.toLong())
    }

    override suspend fun removeFromCart(skuId: Int, sessionId: String) {
        db.cartQueries.removeFromCart(skuId = skuId.toLong(), sessionId = sessionId)
    }

    override suspend fun clearCart(sessionId: String) {
        db.cartQueries.clearCart(sessionId = sessionId)
    }

    override suspend fun checkIfExistsInCart(skuId: Int, sessionId: String): Boolean {
        return db.cartQueries.checkIfExistsInCart(skuId = skuId.toLong(), sessionId = sessionId).executeAsOne() > 0
    }

    override suspend fun getCartItemBySkuId(skuId: Int, sessionId: String): CartDisplay? {
        return db.cartQueries.getCartItemBySkuId(skuId = skuId.toLong(), sessionId = sessionId).executeAsOneOrNull()
    }

    override suspend fun selectAllCartDisplay(): List<CartDisplay> {
        return db.cartQueries.selectAllCartDisplay().executeAsList()
    }

}
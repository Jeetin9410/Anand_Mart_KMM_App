package org.example.project.domain.repository

import com.example.project.Wishlist
import com.example.project.WishlistDisplay

interface WishListRepository {
    fun insert(wishlist: Wishlist)
    fun deleteBySession(sessionId: String)
    fun deleteBySkuId(skuId: Long)
    fun getBySession(sessionId: String): List<Wishlist>
    fun isSkuInWishlist(sessionId: String, skuId: Long): Boolean
    fun getAllWishlistDisplay(): List<WishlistDisplay>
    fun getWishlistDisplayBySession(sessionId: String): List<WishlistDisplay>
    fun getWishlistDisplayBySkuId(skuId: Long): WishlistDisplay?
    fun deleteAllWishList()

}
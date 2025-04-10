package org.example.project.data.repository

import com.example.project.AnandMartDb
import com.example.project.Wishlist
import com.example.project.WishlistDisplay
import org.example.project.domain.repository.WishListRepository

class WishListRepoImpl(private val db: AnandMartDb) : WishListRepository {
    override fun insert(wishlist: Wishlist) {
        db.wishlistQueries.insertWishlist(wishlist.sessionId, wishlist.skuId)
    }

    override fun deleteBySession(sessionId: String) {
        db.wishlistQueries.deleteWishlistBySession(sessionId)
    }

    override fun deleteBySkuId(skuId: Long) {
        db.wishlistQueries.deleteWishlistBySkuId(skuId)
    }

    override fun getBySession(sessionId: String): List<Wishlist> {
       return db.wishlistQueries.getAllWishlist().executeAsList()
    }

    override fun isSkuInWishlist(sessionId: String, skuId: Long): Boolean {
        return db.wishlistQueries.isSkuInWishlist(sessionId, skuId).executeAsOne()
    }

    override fun getAllWishlistDisplay(): List<WishlistDisplay> {
        return db.wishlistQueries.getAllWishlistDisplay().executeAsList()
    }

    override fun getWishlistDisplayBySession(sessionId: String): List<WishlistDisplay> {
        return db.wishlistQueries.getWishlistDisplayBySession(sessionId).executeAsList()
    }

    override fun getWishlistDisplayBySkuId(skuId: Long): WishlistDisplay? {
        return db.wishlistQueries.getWishlistDisplayBySkuId(skuId).executeAsOneOrNull()
    }

    override fun deleteAllWishList() {
       db.wishlistQueries.deleteAllWishlist()
    }

}
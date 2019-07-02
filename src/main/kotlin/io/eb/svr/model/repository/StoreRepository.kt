package io.eb.svr.model.repository

import io.eb.svr.model.entity.B2BUserShop
import io.eb.svr.model.entity.Store
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface StoreRepository : JpaRepository<Store, Long> {
	fun findStoresById(id: Long) : Store?

}

@Repository
interface B2BUserShopRepository : JpaRepository<B2BUserShop, B2BUserShop.B2BUserShopPK> {
	fun findB2BUserShopsByB2BUserShopPK(b2BUserShopPK: B2BUserShop.B2BUserShopPK) : B2BUserShop?
}
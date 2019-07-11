package io.eb.svr.model.repository

import io.eb.svr.model.entity.*
import io.eb.svr.model.enums.Days
import io.eb.svr.model.enums.TimeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Repository
interface StoreRepository : JpaRepository<Store, Long> {
	fun findStoresById(id: Long) : Store?

	fun findStoresByCode(code: String) : Store?
}

@Repository
interface B2BUserShopRepository : JpaRepository<B2BUserShop, B2BUserShop.B2BUserShopPK> {
	fun findB2BUserShopsByB2BUserShopPK(b2BUserShopPK: B2BUserShop.B2BUserShopPK) : B2BUserShop?

	fun findB2BUserShopsByB2BUserShopPKUserIdAndJoinDateLessThanEqualAndLeaveDateGreaterThanEqual(
		userId: Long, startDate: LocalDate, endDate: LocalDate) : B2BUserShop?

	fun findB2BUserShopsByB2BUserShopPKStoreId(storeId: Long) : List<B2BUserShop>
}

@Repository
interface ShopSettingItemRepository: JpaRepository<ShopSettingItem, ShopSettingItem.ShopSettingItemPK> {
	fun findShopSettingItemsByShopSettingItemPKStoreId(storeId: Long): List<ShopSettingItem>

	fun findShopSettingItemsByShopSettingItemPKStoreIdAndShopSettingItemPKItemCode(storeId: Long, itemCode: String): ShopSettingItem?

}

@Repository
interface ShopOperationTimeRepository: JpaRepository<ShopOperationTime, ShopOperationTimePK> {
	@Query("SELECT v from ShopOperationTime v where v.pk.shopId = :shopId and v.pk.typeCode = :typeCode and v.pk.dayCode = :dayCode and v.pk.startDate <= :startDate and v.pk.endDate >= :endDate")
	fun findShopOperationTimesByShopOpeationTimePK(shopId: Long, typeCode: TimeType, dayCode: Days, startDate: LocalDateTime, endDate: LocalDateTime) : ShopOperationTime?

	@Modifying
	@Query("UPDATE ShopOperationTime v set v.pk.endDate = :endDate where v.pk.shopId = :shopId and v.pk.typeCode = :typeCode and v.pk.dayCode = :dayCode and v.pk.startDate = :startDate")
	fun updateShopOperationTimesEndDate(shopId: Long, typeCode: TimeType, dayCode: Days, startDate: LocalDateTime, endDate: LocalDateTime)

	@Query("SELECT v from ShopOperationTime v where v.pk.shopId = :shopId and v.pk.endDate = '9999-12-31 23:59:59'")
	fun findShopOperationTimesByShopOperationTimePKShopId(shopId: Long): List<ShopOperationTime>

//	@Query("SELECT v from ShopOperationTime v where v.pk.shopId = :shopId")
//	fun findShopOperationTimesByShopOpeationTimePK(shopId: Long): ShopOperationTime?

}
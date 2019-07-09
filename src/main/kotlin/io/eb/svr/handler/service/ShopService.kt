package io.eb.svr.handler.service

import io.eb.svr.common.util.DataUtil
import io.eb.svr.common.util.JSONUtil
import io.eb.svr.common.util.S3Client
import io.eb.svr.exception.CustomException
import io.eb.svr.handler.entity.request.ShopRequest
import io.eb.svr.model.entity.B2BUserShop
import io.eb.svr.model.entity.ShopSettingItem
import io.eb.svr.model.entity.Store
import io.eb.svr.model.repository.B2BUserShopRepository
import io.eb.svr.model.repository.ShopSettingItemRepository
import io.eb.svr.model.repository.StoreRepository
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.configurationprocessor.json.JSONObject
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate
import java.time.LocalDateTime
import javax.transaction.Transactional

/**
 * Create by lucy on 2019-06-10
 **/
@Service
class ShopService {
	companion object : KLogging()

	@Autowired
	private lateinit var storeRepository: StoreRepository

	@Autowired
	private lateinit var b2BUserShopRepository: B2BUserShopRepository

	@Autowired
	private lateinit var shopSettingItemRepository: ShopSettingItemRepository

	@Autowired
	private lateinit var s3Client: S3Client

	@Throws(CustomException::class)
	fun searchShopById(storeId: Long): Store {
		return storeRepository.getOne(storeId)
	}

	fun createShop(store: Store): Long {
		return storeRepository.save(store).id!!
	}

	fun createShopSettingItem(shopSettingItem: ShopSettingItem): ShopSettingItem {
		return shopSettingItemRepository.save(shopSettingItem)
	}

	@Throws(CustomException::class)
	fun createB2BUserInShop(b2BUserShop: B2BUserShop): B2BUserShop {
		return b2BUserShopRepository.save(b2BUserShop)
	}

	@Throws(CustomException::class)
	fun searchB2BUserShopByUserId(userId: Long, startDate: LocalDate, endDate: LocalDate): B2BUserShop? {
		return b2BUserShopRepository.findB2BUserShopsByB2BUserShopPKUserIdAndJoinDateLessThanEqualAndLeaveDateGreaterThanEqual(
			userId, startDate, endDate
		)
	}

	@Throws(CustomException::class)
	fun searchShopSettingItemByShopId(storeId: Long): List<ShopSettingItem> {
		return shopSettingItemRepository.findShopSettingItemsByShopSettingItemPKStoreId(storeId)
	}

	@Throws(CustomException::class)
	fun fileUpload(file: MultipartFile) {
		s3Client.upload(file)
	}

	@Throws(CustomException::class)
	@Transactional
//	fun putShop(params: Map<String, Array<String>>, files: Map<String, MultipartFile>) {
	fun putShop(request: String, files: List<MultipartFile>) {
		val errors = HashSet<String>()

		logger.debug { request }

		request.let {
			val json = JSONUtil.getJSONObject(it)
			if (JSONUtil.getJSONObjectLongValue(json, "shopId") <= 0)
				return throw CustomException("Shop Id is empty", HttpStatus.BAD_REQUEST)

			searchShopById(JSONUtil.getJSONObjectLongValue(json, "shopId")).let { newStore ->

				if (!JSONUtil.getJSONObjectStringValue(json, "shopName").isNullOrEmpty())
					newStore.name = JSONUtil.getJSONObjectStringValue(json, "shopName")

				if (!JSONUtil.getJSONObjectStringValue(json, "zip").isNullOrEmpty())
					newStore.zipCode = JSONUtil.getJSONObjectStringValue(json, "zip")

				if (!JSONUtil.getJSONObjectStringValue(json, "address").isNullOrEmpty())
					newStore.address = JSONUtil.getJSONObjectStringValue(json, "address")

				if (!JSONUtil.getJSONObjectStringValue(json, "addressDetail").isNullOrEmpty())
					newStore.addressDetail = JSONUtil.getJSONObjectStringValue(json, "addressDetail")

				if (!JSONUtil.getJSONObjectStringValue(json, "phone1").isNullOrEmpty())
					newStore.phone1 = JSONUtil.getJSONObjectStringValue(json, "phone1")

				if (!JSONUtil.getJSONObjectStringValue(json, "phone2").isNullOrEmpty())
					newStore.phone2 = JSONUtil.getJSONObjectStringValue(json, "phone2")

				if (!JSONUtil.getJSONObjectStringValue(json, "phone3").isNullOrEmpty())
					newStore.phone3 = JSONUtil.getJSONObjectStringValue(json, "phone3")

				if (!JSONUtil.getJSONObjectStringValue(json, "title").isNullOrEmpty())
					newStore.title = JSONUtil.getJSONObjectStringValue(json, "title")

				if (!JSONUtil.getJSONObjectStringValue(json, "onlineYn").isNullOrEmpty())
					newStore.onlineYn = JSONUtil.getJSONObjectStringValue(json, "onlineYn")

				if (JSONUtil.getJSONObjectStringValue(json, "onlieYn").equals("N"))
					newStore.onlineDate = null
				else if (!JSONUtil.getJSONObjectStringValue(json, "onlineDate").isNullOrEmpty())
					newStore.onlineDate = JSONUtil.getJSONObjectDateTimeValue(json, "onlineDate")

				if (!JSONUtil.getJSONObjectStringValue(json, "introduction").isNullOrEmpty())
					newStore.introduction = JSONUtil.getJSONObjectStringValue(json, "introduction")

				if (!JSONUtil.getJSONObjectStringValue(json, "information").isNullOrEmpty())
					newStore.information = JSONUtil.getJSONObjectStringValue(json, "information")

				if (!JSONUtil.getJSONObjectStringValue(json, "latitude").isNullOrEmpty())
					newStore.latitude = JSONUtil.getJSONObjectDoubleValue(json, "latitude")

				if (!JSONUtil.getJSONObjectStringValue(json, "longitude").isNullOrEmpty())
					newStore.longitude = JSONUtil.getJSONObjectDoubleValue(json, "longitude")

				if (!JSONUtil.getJSONObjectStringValue(json, "homepage").isNullOrEmpty())
					newStore.homepage = JSONUtil.getJSONObjectStringValue(json, "homepage")

				if (!JSONUtil.getJSONObjectStringValue(json, "blog").isNullOrEmpty())
					newStore.blog = JSONUtil.getJSONObjectStringValue(json, "blog")

				if (!JSONUtil.getJSONObjectStringValue(json, "instagram").isNullOrEmpty())
					newStore.instagram = JSONUtil.getJSONObjectStringValue(json, "instagram")

				if (!JSONUtil.getJSONObjectStringValue(json, "facebook").isNullOrEmpty())
					newStore.facebook = JSONUtil.getJSONObjectStringValue(json, "facebook")

				if (!JSONUtil.getJSONObjectStringValue(json, "youtube").isNullOrEmpty())
					newStore.youtube = JSONUtil.getJSONObjectStringValue(json, "youtube")

				logger.debug { "store zipCode" + newStore.zipCode }
				logger.debug { "store title [" + newStore.title+"]"}
			}
		}

		files.map { file ->
			val fileName = file.originalFilename
			logger.debug { "file name:"+ fileName }
		}

//		files.forEach { name, file ->
//			logger.debug { "file name:"+ file.originalFilename }
////			try {
////				val attach = attachmentService.add(file)
////				if (attach != null) {
////					result.add(attach)
////				}
////			} catch (e: IllegalStateException) {
////				errors.add(name)
////				throw CRException(e.message!!, e)
////			} catch (e: IOException) {
////				errors.add(name)
////				throw CRException(e.message!!, e)
////			}
//		}
//
//		if (!errors.isEmpty()) {
//			throw CustomException("Error saving files: " + errors.toString(), HttpStatus.BAD_REQUEST)
//		}

	}



}
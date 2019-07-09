package io.eb.svr.handler.service

import io.eb.svr.common.util.DateUtil
import io.eb.svr.exception.CustomException
import io.eb.svr.handler.entity.request.ConfirmReceptShopRequest
import io.eb.svr.handler.entity.request.ReceptShopSearchRequest
import io.eb.svr.handler.entity.request.ShopReceptSearchRequest
import io.eb.svr.model.entity.*
import io.eb.svr.model.enums.ShopSetting
import io.eb.svr.security.jwt.JwtTokenProvider
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException
import javax.servlet.http.HttpServletRequest

/**
 * Create by lucy on 2019-06-25
 **/
@Service
class AdminService {
	companion object : KLogging()

	@Autowired
	private lateinit var tokenProvider: JwtTokenProvider

	@Autowired
	private lateinit var userService: UserService

	@Autowired
	private lateinit var receptShopService: ReceptShopService

	@Autowired
	private lateinit var shopService: ShopService

	@Throws(CustomException::class)
	fun getSelf(servlet: HttpServletRequest): User {
		val token = tokenProvider.resolveTokenOrThrow(servlet)
		val username = tokenProvider.getUsernameOrThrow(token)

		return userService.findByUserEmail(username)
			?: throw CustomException("Invalid token", HttpStatus.UNAUTHORIZED)
	}

	@Throws(CustomException::class)
	fun getReceptShopAll(servlet: HttpServletRequest, request: ReceptShopSearchRequest) : List<ReceptStore> {
		val user = getSelf(servlet)
		logger.debug { "getReceptShopAll id:"+user.id }

		if (user.role != UserRole.ADMIN)
			throw CustomException("Action not allowed", HttpStatus.UNAUTHORIZED)

		return receptShopService.getAll(servlet)
	}

	@Throws(CustomException::class)
	fun putConfirmReceptShopById(servlet: HttpServletRequest, request: ConfirmReceptShopRequest) {
		val user = getSelf(servlet)

		if (user.role != UserRole.ADMIN)
			throw CustomException("Action not allowed", HttpStatus.UNAUTHORIZED)

		val recept = try {
			receptShopService.getBySeq(request.seq)

		} catch (exception: EntityNotFoundException) {
			throw CustomException("recept order not found", HttpStatus.NOT_FOUND)
		}

		val newStore = Store(
			name = recept.storeName,
			serviceType = recept.serviceType,
			city = recept.city,
			district = recept.district
			)

		shopService.createShop(newStore)

		ShopSetting.values().toList().forEach { data : ShopSetting ->
			var settimgItem = ShopSettingItem(
				shopSettingItemPK = ShopSettingItem.ShopSettingItemPK(newStore.id!!, data.type),
				description = data.desc,
				required = data.required,
				icon = data.icon,
				settingYn = "N"
			)
			shopService.createShopSettingItem(settimgItem)
		}

		recept.confirmYn = "Y"
		recept.confirmDate = DateUtil.stringToLocalDateTime(DateUtil.nowDateTime)
		recept.confirmBy = user.id
		recept.storeId = newStore.id

		receptShopService.shopRecept(recept)
	}
}
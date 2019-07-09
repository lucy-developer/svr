package io.eb.svr.handler.service

import io.eb.svr.common.util.DateUtil
import io.eb.svr.common.util.SmsUtil
import io.eb.svr.exception.AlreadyExistsException
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.stereotype.Service
import io.eb.svr.exception.CustomException
import io.eb.svr.handler.entity.request.*
import io.eb.svr.handler.entity.response.LoginResponse
import io.eb.svr.handler.entity.response.ShopReceptResponse
import io.eb.svr.model.entity.*
import io.eb.svr.model.enums.Position
import io.eb.svr.model.enums.ShopRole
import io.eb.svr.model.repository.UserRepository
import io.eb.svr.model.repository.ReceptStoreRepository
import io.eb.svr.security.DefaultValidator
import io.eb.svr.security.jwt.JwtTokenProvider
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDate
import javax.persistence.EntityNotFoundException
import javax.servlet.http.HttpServletRequest
import kotlin.collections.HashMap

/**
 * Create by lucy on 2019-06-03
 **/
@Service
class AuthService {
	companion object : KLogging()
	@Autowired
	private lateinit var authenticationManager: AuthenticationManager

	@Autowired
	private lateinit var passwordEncoder: PasswordEncoder

	@Autowired
	private lateinit var tokenProvider: JwtTokenProvider

	@Autowired
	private lateinit var receptShopService: ReceptShopService

	@Autowired
	private lateinit var smsUtil: SmsUtil

	@Autowired
	private lateinit var shopService: ShopService

	@Autowired
	private lateinit var userService: UserService

	@Throws(CustomException::class)
	fun b2bLogin(servlet: HttpServletRequest, request: LoginRequest): LoginResponse = with(request) {
		try {
			val token = UsernamePasswordAuthenticationToken(request.email, request.password)
			authenticationManager.authenticate(token)

			val role = userService.findByUserEmail(request.email)?.role
				?: throw CustomException("User not found", HttpStatus.NOT_FOUND)

			val password = BCryptPasswordEncoder().encode(request.password)

			val userinfo = HashMap<String, Any>()
			val b2bUser = userService.findByUserEmail(request.email)

			if (b2bUser == null) {
				throw CustomException("Invalid username or password", HttpStatus.UNPROCESSABLE_ENTITY)
			}

			if (b2bUser.role == UserRole.USER) {
				return throw CustomException("Invalid User Role", HttpStatus.UNAUTHORIZED)
			}
			val userShop = shopService.searchB2BUserShopByUserId(b2bUser.id, DateUtil.stringToLocalDate(DateUtil.nowDate), DateUtil.stringToLocalDate(DateUtil.nowDate))
				?: return throw CustomException("Invalid User Shop Info", HttpStatus.UNAUTHORIZED)

			userinfo.put("user", userShop)

			if (userShop.shopRole == ShopRole.ADMIN) {
				//설정 정보 입력 여부 확인
				val items = shopService.searchShopSettingItemByShopId(userShop.b2BUserShopPK.storeId)
				var required = "Y"
				for(item in items) {
					if ((item.required == "Y") && (item.settingYn == "N")) required = "N"
				}
				userinfo.put("shop_setting", required)
			}
			return LoginResponse(tokenProvider.createToken(request.email, role), userinfo)
		} catch (exception: AuthenticationException) {
			throw CustomException("Invalid username or password", HttpStatus.UNPROCESSABLE_ENTITY)
		}
	}

	@Throws(CustomException::class)
	fun b2cLogin(servlet: HttpServletRequest, request: LoginRequest): LoginResponse = with(request) {
		try {
			val token = UsernamePasswordAuthenticationToken(request.email, request.password)
			authenticationManager.authenticate(token)

			val role = userService.findByUserEmail(request.email)?.role
				?: throw CustomException("User not found", HttpStatus.NOT_FOUND)

			val password = BCryptPasswordEncoder().encode(request.password)

			val userinfo = HashMap<String, Any>()
			val b2bUser = userService.findByUserEmail(request.email)

			if (b2bUser == null) {
				throw CustomException("Invalid username or password", HttpStatus.UNPROCESSABLE_ENTITY)
			}

			userinfo.put("user", b2bUser)
			return LoginResponse(tokenProvider.createToken(request.email, role), userinfo)
		} catch (exception: AuthenticationException) {
			throw CustomException("Invalid username or password", HttpStatus.UNPROCESSABLE_ENTITY)
		}
	}

	@Throws(CustomException::class)
	fun shopRecept(servlet: HttpServletRequest, request: ShopReceptRequest) : ShopReceptResponse = with(request) {
		var receptStore : ReceptStore = ReceptStore(
			-1,
			storeName = storeName,
			serviceType = serviceType,
			userName = userName,
			job = job,
			mobile1 = mobile1,
			mobile2 = mobile2,
			mobile3 = mobile3,
			phone1 = phone1,
			phone2 = phone2,
			phone3 = phone3,
			city = city,
			district = district,
			ceoName = ceoName,
			ceoMobile1 = ceoMobile1,
			ceoMobile2 = ceoMobile2,
			ceoMobile3 = ceoMobile3 )

		// 중복 체크
		if (!receptShopService.checkIfShopNameAndCeoIsAlreadyRecept(receptStore))
			throw CustomException("Shop Recept order is Already", HttpStatus.CONFLICT)


		receptStore.seq = receptShopService.shopRecept(receptStore)
		return ShopReceptResponse(receptStore.seq)
	}

	@Throws(CustomException::class)
	fun certNumRequest(servlet: HttpServletRequest, request: CertNumRequest) = with(request) {
		if (!smsUtil.certNumRequest(request)) {
			throw CustomException("Action not allowed", HttpStatus.CONFLICT)
		}
	}

	@Throws(CustomException::class)
	fun certNumConfirm(servlet: HttpServletRequest, request: CertNumRequest) = with(request) {
		if (!smsUtil.CertNumConfirm(request)) {
			throw CustomException("Action not allowed", HttpStatus.FORBIDDEN)
		}
	}

	@Throws(CustomException::class)
	fun shopReceptSearch(servlet: HttpServletRequest, request: ShopReceptSearchRequest) : HashMap<String, Any> {
		val data = receptShopService.shopReceptSearch(request)
//		val result = HashM

		if (data.isEmpty()) {
			logger.debug {"shopReceptSearch is null"}
			throw CustomException("Shop Recept order not found", HttpStatus.NOT_FOUND)
		}
		return data
	}

	@Throws(CustomException::class)
	fun putShop(servlet: HttpServletRequest, request: ShopRequest) {
		val shop = try {
			shopService.searchShopById(request.shopId)
		} catch (exception: EntityNotFoundException) {
			throw CustomException("shopId not found", HttpStatus.NOT_FOUND)
		}

		val newShop = Store(
			id = shop.id,
			name = request.shopName,
			zipCode = request.zip,
			address = request.address,
			addressDetail = request.addressDetail,
			latitude = request.latitude,
			longitude = request.longitude,
			phone1 = request.phone1,
			phone2 = request.phone2,
			phone3 = request.phone3,
			city = request.city,
			district = request.district
		)

		shopService.createShop(newShop)
	}

	@Throws(AlreadyExistsException::class)
	fun checkAccountIsAlreadyUsed(request: CheckAccountRequest) : HashMap<String, Any> {
		val userinfo = HashMap<String, Any>()

		if (userService.existsByEmail(request)) {
			val user = userService.findByUserEmail(request.email)
//			userinfo.put("user", user!!)
			userinfo.put("user_store", shopService.searchB2BUserShopByUserId(user!!.id, DateUtil.stringToLocalDate(DateUtil.nowDate), DateUtil.stringToLocalDate(DateUtil.nowDate))!!)
			return userinfo
		} else {
			throw CustomException("user not found", HttpStatus.NOT_FOUND)
		}
	}

	@Throws(CustomException::class)
	fun b2cUserRegister(request: UserRegisterRequest) = with(request) {
		val user = userService.findByUserEmail(request.email)

		if (user != null) {
			throw CustomException("UserEmail is already in use", HttpStatus.CONFLICT)
		} else {
			val newUser = User(
				id = -1,
				email = email,
				password = passwordEncoder.encode(password),
				username = name,
				mobile1 = mobile1,
				mobile2 = mobile2,
				mobile3 = mobile3,
				role = UserRole.USER
			)
			DefaultValidator.validate(newUser)
			newUser.id = userService.createUser(newUser).id
		}
	}

	@Throws(CustomException::class)
	fun b2bUserRegister(request: UserRegisterRequest) = with(request) {
		val user = userService.findByUserEmail(request.email)

		if (user != null) {
			logger.debug { "b2bUserRegister user.id ["+user.id+"] request.id["+id +"]"}
			if ((user.id != id) || (user.mobile2 != mobile2) || (user.mobile3 != mobile3))
				return throw CustomException("UserEmail is already in use", HttpStatus.CONFLICT)
		}

		shopService.searchB2BUserShopByUserId(user!!.id, DateUtil.stringToLocalDate(DateUtil.nowDate), DateUtil.stringToLocalDate(DateUtil.nowDate)).let { b2bUserShop ->
			return throw CustomException("User already have a shop", HttpStatus.CONFLICT)
		}

		val newUser = User(
			id = id!!,
			email = email,
			password = passwordEncoder.encode(password),
			username = name,
			mobile1 = mobile1,
			mobile2 = mobile2,
			mobile3 = mobile3,
			role = UserRole.SHOP
		)
		DefaultValidator.validate(newUser)
		newUser.id = userService.createUser(newUser).id

		val newUserShopPk = B2BUserShop.B2BUserShopPK(userId = newUser.id, storeId = storeId!!)
		val newUserShop = B2BUserShop(
			b2BUserShopPK = newUserShopPk,
			position = position!!,
			joinDate = joinDate!!,
			nickName = nickName!!,
			salaryBank = salaryBankCode!!,
			salaryBankNumber = salaryBankNum!!,
			shopRole = role!!
		)

		shopService.createB2BUserInShop(newUserShop)
		if (position == Position.CEO) {
			shopService.searchShopById(storeId).let { newStore ->
				shopService.createShop(newStore.apply {
					ceoId = newUser.id
				})
			}
		}
	}

	@Throws(CustomException::class)
	fun adminUserRegister(request: UserRegisterRequest) = with(request) {
		val user = userService.findByUserEmail(request.email)

		if (user != null) {
			throw CustomException("UserEmail is already in use", HttpStatus.CONFLICT)
		} else {
			val newUser = User(
				id = -1,
				email = email,
				password = passwordEncoder.encode(password),
				username = name,
				mobile1 = mobile1,
				mobile2 = mobile2,
				mobile3 = mobile3,
				role = UserRole.ADMIN
			)
			DefaultValidator.validate(newUser)
			newUser.id = userService.createUser(newUser).id
		}
	}
}
package io.eb.svr.handler.service

import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.stereotype.Service
import io.eb.svr.exception.CustomException
import io.eb.svr.handler.entity.request.LoginRequest
import io.eb.svr.handler.entity.request.ShopReceptRequest
import io.eb.svr.handler.entity.response.LoginResponse
import io.eb.svr.handler.entity.response.ShopReceptResponse
import io.eb.svr.model.entity.ReceptStore
import io.eb.svr.model.repository.B2BUserRepository
import io.eb.svr.model.repository.ReceptStoreRepository
import io.eb.svr.security.jwt.JwtTokenProvider
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.*
import javax.servlet.http.HttpServletRequest
import kotlin.collections.HashMap
import kotlin.math.log
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

/**
 * Create by lucy on 2019-06-03
 **/
@Service
class AuthService {
	companion object : KLogging()
	@Autowired
	private lateinit var authenticationManager: AuthenticationManager

	@Autowired
	private lateinit var b2BUserRepository: B2BUserRepository

	@Autowired
	private lateinit var tokenProvider: JwtTokenProvider

	@Autowired
	private lateinit var receptStoreRepository: ReceptStoreRepository

	@Throws(CustomException::class)
	fun b2bLogin(servlet: HttpServletRequest, request: LoginRequest): LoginResponse = with(request) {
		try {
			val token = UsernamePasswordAuthenticationToken(request.username, request.password)
			authenticationManager.authenticate(token)

			val role = b2BUserRepository.findById(request.username)?.role
				?: throw CustomException("User not found", HttpStatus.NOT_FOUND)

			val password = BCryptPasswordEncoder().encode(request.password)

			logger.info("b2bUserLogin id:" + request.username + " password:"+request.password)

			val userinfo = HashMap<String, String>()
//			val b2bUser = b2BUserRepository.findB2BUsersByIdAndPassword(request.username, password)
			val b2bUser = b2BUserRepository.findById(request.username)

			if (b2bUser == null) {
				throw CustomException("Invalid username or password", HttpStatus.UNPROCESSABLE_ENTITY)
			}

			logger.info { "b2bLogin response" }
			userinfo.put("id", b2bUser.id!!)
//				userinfo.put("role", role)
			userinfo.put("username", b2bUser.username!!)
			return LoginResponse(tokenProvider.createToken(request.username, role), userinfo)
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

		receptStore.seq = receptStoreRepository.save(receptStore).seq
		return ShopReceptResponse(receptStore.seq)
	}
}
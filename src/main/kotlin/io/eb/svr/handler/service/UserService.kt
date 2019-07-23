package io.eb.svr.handler.service

import io.eb.svr.exception.CustomException
import io.eb.svr.handler.entity.request.CheckAccountRequest
import io.eb.svr.model.entity.User
import io.eb.svr.model.repository.UserRepository
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

/**
 * Create by lucy on 2019-06-25
 **/
@Service
class UserService {
	companion object : KLogging()

	@Autowired
	private lateinit var userRepository: UserRepository

	@Throws(CustomException::class)
	fun findByUserEmail(email: String) : User? {
		return userRepository.findUsersByEmail(email)
	}

	@Throws(CustomException::class)
	fun findByUserId(id: Long) : User? {
		return userRepository.findUsersById(id)
	}

	@Throws(CustomException::class)
	fun existsByEmail(request: CheckAccountRequest) : Boolean {
		return userRepository.existsUsersByEmail(request.email)
	}

	@Throws(CustomException::class)
	fun findByUserMobile3(servlet: HttpServletRequest, mobile3: String) : List<User> {
		return userRepository.findUsersByMobile3(mobile3)
	}

	@Throws(CustomException::class)
	fun existsByAccount(request: CheckAccountRequest) : Boolean {
		return userRepository.existsUsersByEmailAndUsernameAndMobile1AndMobile2AndMobile3(
			request.email, request.name, request.mobile1, request.mobile2, request.mobile3
		)
	}

	@Throws(CustomException::class)
	fun createUser(request: User): User {
		return userRepository.save(request)
	}

	@Throws(CustomException::class)
	fun findB2BUserShopRelation() {
	}


}
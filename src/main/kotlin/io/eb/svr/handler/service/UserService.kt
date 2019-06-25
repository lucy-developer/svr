package io.eb.svr.handler.service

import io.eb.svr.exception.CustomException
import io.eb.svr.model.entity.User
import io.eb.svr.model.repository.UserRepository
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

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
		return userRepository.findById(email)
	}

}
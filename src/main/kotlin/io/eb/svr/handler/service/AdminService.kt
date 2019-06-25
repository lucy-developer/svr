package io.eb.svr.handler.service

import io.eb.svr.exception.CustomException
import io.eb.svr.model.entity.ReceptStore
import io.eb.svr.model.entity.User
import io.eb.svr.model.entity.UserRole
import io.eb.svr.security.jwt.JwtTokenProvider
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
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

	@Throws(CustomException::class)
	fun getSelf(servlet: HttpServletRequest): User {
		val token = tokenProvider.resolveTokenOrThrow(servlet)
		val username = tokenProvider.getUsernameOrThrow(token)

		return userService.findByUserEmail(username)
			?: throw CustomException("Invalid token", HttpStatus.UNAUTHORIZED)
	}

	@Throws(CustomException::class)
	fun getReceptShopAll(servlet: HttpServletRequest) : List<ReceptStore> {
		val user = getSelf(servlet)
		logger.debug { "getReceptShopAll id:"+user.id }

		if (user.role != UserRole.ADMIN)
			throw CustomException("Action not allowed", HttpStatus.UNAUTHORIZED)

		return receptShopService.getAll(servlet)
	}
}
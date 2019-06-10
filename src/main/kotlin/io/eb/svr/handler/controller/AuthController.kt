package io.eb.svr.handler.controller

import io.eb.svr.common.config.ApiConfig.API_VERSION
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.OK
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import io.eb.svr.common.config.ApiConfig.AUTH_LOGIN_PATH
import io.eb.svr.common.config.ApiConfig.AUTH_PATH
import io.eb.svr.common.config.ApiConfig.AUTH_REGISTER_PATH
import io.eb.svr.common.config.ApiConfig.B2B_PATH
import io.eb.svr.handler.entity.request.LoginRequest
import io.eb.svr.handler.service.AuthService
import javax.servlet.http.HttpServletRequest

/**
 * Create by lucy on 2019-06-03
 **/
@RestController
@RequestMapping(
	path = ["/$API_VERSION/$AUTH_PATH"]
)
class AuthController {
	@Autowired
	private lateinit var authService: AuthService

	@PostMapping(
		path = ["/$B2B_PATH/$AUTH_LOGIN_PATH"],
		consumes = [APPLICATION_JSON_VALUE],
		produces = [APPLICATION_JSON_VALUE]
	)
	fun b2bLogin(
		servlet: HttpServletRequest,
		@RequestBody request: LoginRequest
	) = ResponseEntity.status(OK).body(authService.b2bLogin(servlet, request))

	@PostMapping(
		path = ["/$B2B_PATH/$AUTH_REGISTER_PATH"],
		consumes = [APPLICATION_JSON_VALUE],
		produces = [APPLICATION_JSON_VALUE]
	)
	fun b2bRegister(
		servlet: HttpServletRequest,
		@RequestBody request: LoginRequest
	) = ResponseEntity.status(OK).body(authService.b2bLogin(servlet, request))
}
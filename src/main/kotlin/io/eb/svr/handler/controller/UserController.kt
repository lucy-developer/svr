package io.eb.svr.handler.controller

import io.eb.svr.common.config.ApiConfig.API_VERSION
import io.eb.svr.common.config.ApiConfig.B2C_PATH
import io.eb.svr.common.config.ApiConfig.MOBILE_PATH
import io.eb.svr.common.config.ApiConfig.USER_PATH
import io.eb.svr.handler.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import software.amazon.awssdk.http.HttpStatusCode.OK
import javax.servlet.http.HttpServletRequest

/**
 * Create by lucy on 2019-07-22
 **/
@RestController
@RequestMapping(
	path = ["/${API_VERSION}/${USER_PATH}"]
)
class UserController {
	@Autowired
	private lateinit var userService: UserService

	@GetMapping(
		path = ["/$MOBILE_PATH/{mobile3}"],
		produces = [APPLICATION_JSON_VALUE])
	fun getUsersByMobile3(
		servlet: HttpServletRequest,
		@PathVariable("mobile3") mobile3: String
	) = ResponseEntity.status(OK).body(userService.findByUserMobile3(servlet, mobile3))

}
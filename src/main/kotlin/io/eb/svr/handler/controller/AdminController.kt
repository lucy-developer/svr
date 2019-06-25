package io.eb.svr.handler.controller

import io.eb.svr.common.config.ApiConfig.ADMIN_PATH
import io.eb.svr.common.config.ApiConfig.API_VERSION
import io.eb.svr.common.config.ApiConfig.RECEPT_PATH
import io.eb.svr.common.config.ApiConfig.SEARCH_PATH
import io.eb.svr.handler.service.AdminService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

/**
 * Create by lucy on 2019-06-25
 **/
@RestController
@RequestMapping(
	path = ["/${API_VERSION}/$ADMIN_PATH"]
)
class AdminController {

	@Autowired
	private lateinit var adminService: AdminService

	@GetMapping(
		path = ["/$SEARCH_PATH/$RECEPT_PATH"],
		produces = [MediaType.APPLICATION_JSON_VALUE]
	)
	fun getReceptShopAll(
		servlet: HttpServletRequest
	) = ResponseEntity.status(HttpStatus.OK).body(adminService.getReceptShopAll(servlet))

//	@PostMapping(
//		path = ["/"]
//	)

}
package io.eb.svr.handler.controller

import io.eb.svr.common.config.ApiConfig.ALL_PATH
import io.eb.svr.common.config.ApiConfig.API_VERSION
import io.eb.svr.common.config.ApiConfig.EMPLOYEES_PATH
import io.eb.svr.common.config.ApiConfig.SHOP_PATH
import io.eb.svr.handler.service.EmployeeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

/**
 * Create by lucy on 2019-07-11
 **/
@RestController
@RequestMapping(
	path = ["/${API_VERSION}/${SHOP_PATH}/${EMPLOYEES_PATH}"]
)
class EmployeeController {
	@Autowired
	private lateinit var employeeService: EmployeeService

	@GetMapping(
		path = ["/$ALL_PATH/{shopId}"],
		produces = [APPLICATION_JSON_VALUE])
	fun getEmployeesByShop(
		servlet: HttpServletRequest,
		@PathVariable("shopId") shopId: Long
	) = ResponseEntity.status(OK).body(employeeService.getShopEmployees(servlet, shopId))
}
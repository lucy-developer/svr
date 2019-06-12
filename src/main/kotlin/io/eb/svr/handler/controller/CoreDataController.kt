package io.eb.svr.handler.controller

import io.eb.svr.common.config.ApiConfig
import io.eb.svr.common.config.ApiConfig.API_VERSION
import io.eb.svr.common.config.ApiConfig.AREA_PATH
import io.eb.svr.common.config.ApiConfig.CITIES_PATH
import io.eb.svr.common.config.ApiConfig.CITY_PATH
import io.eb.svr.common.config.ApiConfig.CORE_PATH
import io.eb.svr.handler.service.CoreDataService
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.OK
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

/**
 * Create by lucy on 2019-06-11
 **/
@RestController
@RequestMapping(
	path = ["/$API_VERSION/$CORE_PATH"]
)
class CoreDataController {
	companion object : KLogging()

	@Autowired
	private lateinit var coreDataService: CoreDataService

	@GetMapping(
		path = ["/$AREA_PATH/$CITIES_PATH"],
		produces = [APPLICATION_JSON_VALUE])
	fun getCities(
		servlet: HttpServletRequest
	) = ResponseEntity.status(OK).body(coreDataService.getCities(servlet))

	@GetMapping(
		path = ["/$AREA_PATH/$CITY_PATH/{city}"],
		produces = [APPLICATION_JSON_VALUE])
	fun getGusByCity(
		servlet: HttpServletRequest,
		@PathVariable("city") city: String
	) = ResponseEntity.status(OK).body(coreDataService.getGusByCity(servlet, city))
}
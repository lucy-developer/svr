package io.eb.svr.handler.controller

import io.eb.svr.common.config.ApiConfig.API_VERSION
import io.eb.svr.common.config.ApiConfig.PRODUCT_PATH
import io.eb.svr.common.config.ApiConfig.SERVICE_PATH
import io.eb.svr.common.config.ApiConfig.SHOP_PATH
import io.eb.svr.handler.entity.request.ServiceProductRequest
import io.eb.svr.handler.service.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import software.amazon.awssdk.http.HttpStatusCode.OK
import javax.servlet.http.HttpServletRequest

/**
 * Create by lucy on 2019-07-18
 **/
@RestController
@RequestMapping(
	path = ["/${API_VERSION}/${SHOP_PATH}/${PRODUCT_PATH}"]
)
class ProductController {
	@Autowired
	private lateinit var productService: ProductService

	@GetMapping(
		path = ["/$SERVICE_PATH/{shopId}"],
		produces = [APPLICATION_JSON_VALUE])
	fun getServiceProduct(
		servlet: HttpServletRequest,
		@PathVariable("shopId") shopId: Long
	) = ResponseEntity.status(OK).body(productService.getServiceProductByShopId(servlet, shopId))

	@PostMapping(
		path = ["/$SERVICE_PATH"],
		consumes = [APPLICATION_JSON_VALUE],
		produces = [APPLICATION_JSON_VALUE])
	fun createServiceProduct(
		servlet: HttpServletRequest,
		@RequestBody request: ServiceProductRequest
	) = ResponseEntity.status(HttpStatus.CREATED).body(productService.createServiceProduct(servlet, request))



}
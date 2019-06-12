package io.eb.svr.handler.controller

import io.eb.svr.common.config.ApiConfig
import io.eb.svr.common.config.ApiConfig.API_VERSION
import io.eb.svr.common.config.ApiConfig.SHOP_PATH
import io.eb.svr.handler.service.ShopService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Create by lucy on 2019-06-10
 **/
@RestController
@RequestMapping(
	path = ["/${API_VERSION}/$SHOP_PATH"]
)
class ShopController {
	@Autowired
	private lateinit var shopService: ShopService

}
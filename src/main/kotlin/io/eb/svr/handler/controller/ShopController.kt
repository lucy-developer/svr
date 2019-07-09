package io.eb.svr.handler.controller

import io.eb.svr.common.config.ApiConfig
import io.eb.svr.common.config.ApiConfig.API_VERSION
import io.eb.svr.common.config.ApiConfig.IMAGE_PATH
import io.eb.svr.common.config.ApiConfig.SHOP_PATH
import io.eb.svr.common.config.ApiConfig.UPLOAD_PATH
import io.eb.svr.handler.entity.request.ShopRequest
import io.eb.svr.handler.service.ShopService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.OK
import org.springframework.http.MediaType
import org.springframework.http.MediaType.MULTIPART_FORM_DATA
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.MultipartHttpServletRequest
import javax.servlet.http.HttpServletRequest

/**
 * Create by lucy on 2019-06-10
 **/
@RestController
@RequestMapping(
	path = ["/$API_VERSION/$SHOP_PATH"]
)
class ShopController {
	@Autowired
	private lateinit var shopService: ShopService

	@PostMapping(
		path = ["/$UPLOAD_PATH/$IMAGE_PATH"],
		consumes = arrayOf(MediaType.MULTIPART_FORM_DATA_VALUE),
		produces = [MediaType.APPLICATION_FORM_URLENCODED_VALUE]
	)
	fun fileUpload(
		@RequestParam("file") file: MultipartFile
	) = ResponseEntity.status(OK).body(shopService.fileUpload(file))

	@PutMapping(
		consumes = arrayOf(MediaType.MULTIPART_FORM_DATA_VALUE)
//		,
//		produces = [MediaType.APPLICATION_FORM_URLENCODED_VALUE]
	)
	fun putShop(
		servlet: HttpServletRequest,
		@RequestParam("files") files: List<MultipartFile>,
		@RequestParam("shop") request: String
	) = ResponseEntity.status(OK).body(shopService.putShop(request, files))


}
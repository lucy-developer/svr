package io.eb.svr.handler.controller

import io.eb.svr.common.config.ApiConfig.ACCOUNT_USE_CHECK_PATH
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.OK
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import io.eb.svr.common.config.ApiConfig.API_VERSION
import io.eb.svr.common.config.ApiConfig.AUTH_PATH
import io.eb.svr.common.config.ApiConfig.B2B_PATH
import io.eb.svr.common.config.ApiConfig.B2C_PATH
import io.eb.svr.common.config.ApiConfig.CERTIFICATION_CONFIRM_PATH
import io.eb.svr.common.config.ApiConfig.CERTIFICATION_REQUEST_PATH
import io.eb.svr.common.config.ApiConfig.LOGIN_PATH
import io.eb.svr.common.config.ApiConfig.RECEPT_PATH
import io.eb.svr.common.config.ApiConfig.REGISTER_PATH
import io.eb.svr.common.config.ApiConfig.SEARCH_PATH
import io.eb.svr.common.config.ApiConfig.SHOP_PATH
import io.eb.svr.common.config.ApiConfig.USER_PATH
import io.eb.svr.handler.entity.request.*
import io.eb.svr.handler.service.AuthService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
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
		path = ["/$B2B_PATH/$LOGIN_PATH"],
		consumes = [APPLICATION_JSON_VALUE],
		produces = [APPLICATION_JSON_VALUE]
	)
	fun b2bLogin(
		servlet: HttpServletRequest,
		@RequestBody request: LoginRequest
	) = ResponseEntity.status(OK).body(authService.b2bLogin(servlet, request))

	@PostMapping(
		path = ["/$B2C_PATH/$LOGIN_PATH"],
		consumes = [APPLICATION_JSON_VALUE],
		produces = [APPLICATION_JSON_VALUE]
	)
	fun b2cLogin(
		servlet: HttpServletRequest,
		@RequestBody request: LoginRequest
	) = ResponseEntity.status(OK).body(authService.b2cLogin(servlet, request))

	@PostMapping(
		path = ["/$B2B_PATH/$REGISTER_PATH"],
		consumes = [APPLICATION_JSON_VALUE],
		produces = [APPLICATION_JSON_VALUE]
	)
	fun b2bRegister(
		servlet: HttpServletRequest,
		@RequestBody request: LoginRequest
	) = ResponseEntity.status(OK).body(authService.b2bLogin(servlet, request))

	@PostMapping(
		path = ["/$SHOP_PATH/$RECEPT_PATH"],
		consumes = [APPLICATION_JSON_VALUE],
		produces = [APPLICATION_JSON_VALUE]
	)
	fun shopRecept(
		servlet: HttpServletRequest,
		@RequestBody request: ShopReceptRequest
	) = ResponseEntity.status(CREATED).body(authService.shopRecept(servlet, request))

	// SMS 인증 번호 발송
	@PostMapping(
		path = ["/$CERTIFICATION_REQUEST_PATH"],
		consumes = [APPLICATION_JSON_VALUE],
		produces = [APPLICATION_JSON_VALUE]
	)
	fun shopReceptCertNumRequest(
		servlet: HttpServletRequest,
		@RequestBody request: CertNumRequest
	) = ResponseEntity.status(OK).body(authService.certNumRequest(servlet,request))

	@PostMapping(
		path = ["/$CERTIFICATION_CONFIRM_PATH"],
		consumes = [APPLICATION_JSON_VALUE],
		produces = [APPLICATION_JSON_VALUE]
	)
	fun certNumConfirm(
		servlet: HttpServletRequest,
		@RequestBody request: CertNumRequest
	) = ResponseEntity.status(OK).body(authService.certNumConfirm(servlet,request))

	// Shop 신청 문의 조회
	@PostMapping(
		path = ["/$SHOP_PATH/$RECEPT_PATH/$SEARCH_PATH"],
		consumes = [APPLICATION_JSON_VALUE],
		produces = [APPLICATION_JSON_VALUE]
	)
	fun shopReceptSearch(
		servlet: HttpServletRequest,
		@RequestBody request: ShopReceptSearchRequest
	) = ResponseEntity.status(OK).body(authService.shopReceptSearch(servlet,request))

	// 가맹점 정보 입력
	@PutMapping(
		path = ["/$SHOP_PATH"],
		consumes = [APPLICATION_JSON_VALUE]
	)
	fun putShop(
		servlet: HttpServletRequest,
		@RequestBody request: ShopRequest
	) = ResponseEntity.status(OK).body(authService.putShop(servlet,request))

	@PostMapping(
		path = ["/$USER_PATH/$ACCOUNT_USE_CHECK_PATH"],
		consumes = [APPLICATION_JSON_VALUE],
		produces = [APPLICATION_JSON_VALUE]
	)
	fun checkAccountIsAlreadyUsed(
		@RequestBody request: CheckAccountRequest
	) = ResponseEntity.status(OK).body(authService.checkAccountIsAlreadyUsed(request))

	// B2C 가입 신청
	@PostMapping(
		path = ["/$USER_PATH/$B2C_PATH/$REGISTER_PATH"],
		consumes = [APPLICATION_JSON_VALUE],
		produces = [APPLICATION_JSON_VALUE]
	)
	fun b2cUserRegister(
		servlet: HttpServletRequest,
		@RequestBody request: UserRegisterRequest
	) = ResponseEntity.status(CREATED).body(authService.b2cUserRegister(request))

	// B2B 가입 신청
	@PostMapping(
		path = ["/$USER_PATH/$B2B_PATH/$REGISTER_PATH"],
		consumes = [APPLICATION_JSON_VALUE],
		produces = [APPLICATION_JSON_VALUE]
	)
	fun b2bUserRegister(
		servlet: HttpServletRequest,
		@RequestBody request: UserRegisterRequest
	) = ResponseEntity.status(CREATED).body(authService.b2bUserRegister(request))

}
package io.eb.svr.handler.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Create by lucy on 2019-06-05
 **/
@RestController
class TestController {
	@GetMapping(path = ["/test"])
	fun test() = ResponseEntity.status(HttpStatus.OK)

}
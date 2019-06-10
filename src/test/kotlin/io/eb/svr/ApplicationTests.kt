package io.eb.svr

import org.hamcrest.Matchers
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import io.eb.svr.model.entity.B2BUser

/**
 * Create by lucy on 2019-06-03
 **/
@RunWith(SpringRunner::class)
@SpringBootTest
class ApplicationTests {
	@Autowired
	private lateinit var mockMvc: MockMvc

	@Test
	fun contextLoads() {
	}

	private val USER_ADMIN =
		B2BUser("test3",  "김지연", "12345", "F", "010", "4008", "7039")

	private val LOGIN_REQUEST_MIKOLAJ = """{
	"username": "${USER_ADMIN.id}",
    "password": "${USER_ADMIN.password}"
}
"""

	@Test
	fun post_api_b2b_login() {
		mockMvc.perform(
			MockMvcRequestBuilders.post("/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(LOGIN_REQUEST_MIKOLAJ))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk)
			.andExpect(MockMvcResultMatchers.jsonPath("$.token", Matchers.notNullValue()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.user.id", Matchers.`is`(USER_ADMIN.id)))
	}

}
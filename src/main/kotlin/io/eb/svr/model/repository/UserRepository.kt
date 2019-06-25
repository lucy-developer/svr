package io.eb.svr.model.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import io.eb.svr.model.entity.User
import java.util.*
//import javax.annotation.Nullable

@Repository
interface UserRepository : JpaRepository<User, Long> {
	//@Nullable
	fun findById(id: String) : User? // Optional<User>
	fun countById(id: String) : Int

	fun findB2BUsersByIdAndPassword(id: String, password: String) : User?

	fun findByUsername(name: String) : User?

	//@Query("SELECT u FROM User u WHERE u.mobile1 = ?1")
	fun findB2BUsersByMobile1(mobile1: String) : Optional<User>

	//@Query("SELECT u FROM User u WHERE u.mobile1 = :mobile1 AND u.mobile2 = :mobile2 AND u.mobile3 = :mobile3")
	//fun findB2BUsersByMobile1AndAndMobile2AndMobile3(@Param("mobile1") mobile1: String?, @Param("mobile2") mobile2: String?, @Param("mobile3") mobile3: String?) : Optional<User>
	fun findB2BUsersByMobile1AndAndMobile2AndMobile3(mobile1: String, mobile2: String, mobile3: String) : Optional<User>
}
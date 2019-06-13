package io.eb.svr.model.repository

import io.eb.svr.model.entity.Stplat
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Create by lucy on 2019-06-12
 **/
@Repository
interface StplatRepository : JpaRepository<Stplat, Long> {
	fun findFirstByTypeOrderByCreateDateDesc(type: String) : Stplat?
}
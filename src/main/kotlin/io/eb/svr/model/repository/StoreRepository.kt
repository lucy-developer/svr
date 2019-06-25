package io.eb.svr.model.repository

import io.eb.svr.model.entity.Store
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface StoreRepository : JpaRepository<Store, Long> {
	fun findStoresById(id: Long) : Store?

}
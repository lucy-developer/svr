package io.eb.svr.model.repository

import io.eb.svr.model.entity.SmsCertify
import io.eb.svr.model.entity.SmsCertifyPk
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

/**
 * Create by lucy on 2019-06-14
 **/
@Repository
interface SmsCertifyRepository : JpaRepository<SmsCertify, SmsCertifyPk> {
	fun findSmsCertifiesByPk(smsCertifyPk: SmsCertifyPk) : SmsCertify?

	fun findSmsCertifiesByPkAndCertNumber(smsCertifyPk: SmsCertifyPk, certNumber: String) : SmsCertify?

	fun findByPkDate(date: String) : SmsCertify?
	fun findSmsCertifiesByCertNumber(number: String) : SmsCertify?
}
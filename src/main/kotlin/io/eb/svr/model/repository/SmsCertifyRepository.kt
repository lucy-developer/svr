package io.eb.svr.model.repository

import io.eb.svr.model.entity.SmsCertify
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

/**
 * Create by lucy on 2019-06-14
 **/
@Repository
interface SmsCertifyRepository : JpaRepository<SmsCertify, SmsCertify.SmsCertifyPk> {
	fun findSmsCertifiesByPk(smsCertifyPk: SmsCertify.SmsCertifyPk) : SmsCertify?

	fun findSmsCertifiesByPkAndCertNumber(smsCertifyPk: SmsCertify.SmsCertifyPk, certNumber: String) : SmsCertify?

	fun findByPkDate(date: String) : SmsCertify?
	fun findSmsCertifiesByCertNumber(number: String) : SmsCertify?
}
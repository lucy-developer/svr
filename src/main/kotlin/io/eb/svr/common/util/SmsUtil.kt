package io.eb.svr.common.util

import io.eb.svr.common.config.UtilConfig.SMS_LIMIT_COUNT
import io.eb.svr.exception.CustomException
import io.eb.svr.handler.entity.request.CertNumRequest
import io.eb.svr.model.entity.SmsCertify
import io.eb.svr.model.entity.SmsCertifyPk
import io.eb.svr.model.repository.SmsCertifyRepository
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

/**
 * Create by lucy on 2019-06-13
 **/
@Component
class SmsUtil {
	companion object : KLogging()

	@Autowired
	private lateinit var smsCertifyRepository: SmsCertifyRepository
//	private var smsCertifyRepository: SmsCertifyRepository

	private var sms_limits = SMS_LIMIT_COUNT

	@Throws(CustomException::class)
	fun certNumRequest(request: CertNumRequest) : Boolean {

		val pk = SmsCertifyPk(
			date = DateUtil.nowDate.replace("-",""),
			mobile = request.mobile1+request.mobile2+request.mobile3 )
		var count = 0L

		//mobile+발행일자 확인
		val list = smsCertifyRepository.findSmsCertifiesByPk(pk)
		logger.info { "certNumRequest save smsCertify:" + request.mobile1+request.mobile2+request.mobile3 + " date:" + DateUtil.nowDate.replace("-","")}
		if (list == null) {
			count++
		} else {
			if (list.count > SMS_LIMIT_COUNT)
				return false
			count = list.count+1
		}

		//랜덤 숫자 발행
		val number = "12345"
		val smsCertify = SmsCertify(
			pk = pk,
			count = count,
			certNumber = number)

		smsCertifyRepository.save(smsCertify)

		return true
	}

	@Throws(CustomException::class)
	fun CertNumConfirm(request: CertNumRequest) : Boolean {
		val pk = SmsCertifyPk(
			date = DateUtil.nowDate.replace("-",""),
			mobile = request.mobile1+request.mobile2+request.mobile3 )

		smsCertifyRepository.findSmsCertifiesByPkAndCertNumber(pk, request.number).let {
			return true
		}.let {
			return false
		}
	}
}
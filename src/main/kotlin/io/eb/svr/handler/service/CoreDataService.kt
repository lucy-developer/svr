package io.eb.svr.handler.service

import io.eb.svr.exception.CustomException
import io.eb.svr.model.entity.Stplat
import io.eb.svr.model.enums.AreaGroup
import io.eb.svr.model.enums.BankCode
import io.eb.svr.model.enums.Position
import io.eb.svr.model.repository.StplatRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

/**
 * Create by lucy on 2019-06-11
 **/
@Service
class CoreDataService {
	@Autowired
	private lateinit var stplatRepository: StplatRepository

	@Throws(CustomException::class)
	fun getCities(servlet: HttpServletRequest): ArrayList<Map<String, String>> {
		var cities = ArrayList<Map<String, String>>()

		AreaGroup.values().toList().forEach { data : AreaGroup ->
			var city = HashMap<String, String>()
			city.put("value", data.name)
			city.put("label", data.viewName)
			cities.add(city)
		}
		if (cities.isEmpty()) throw CustomException("cities data not found", HttpStatus.NOT_FOUND)
		return cities
	}

	@Throws(CustomException::class)
	fun getGusByCity(servlet: HttpServletRequest, city: String) : ArrayList<Map<String, String>> {
		var gus = ArrayList<Map<String, String>>()

		AreaGroup.values().toList().forEach { data: AreaGroup ->
			if (data.name == city) {
				var items = data.getGroup(city).containProduct
				for (item in items) {
					var gu = HashMap<String, String>()
					gu.put("value", item.name)
					gu.put("label", item.viewName)
					gus.add(gu)
				}
			}
		}
		if (gus.isEmpty()) throw CustomException("gus data not found", HttpStatus.NOT_FOUND)
		return gus
	}

	@Throws(CustomException::class)
	fun getStplatByType(servlet: HttpServletRequest, type: String) : Stplat {
		return stplatRepository.findFirstByTypeOrderByCreateDateDesc(type)
			?: throw CustomException("User not found", HttpStatus.NOT_FOUND)
	}

	@Throws(CustomException::class)
	fun getShopPosition(servlet: HttpServletRequest): ArrayList<Map<String, String>> {
		var positions = ArrayList<Map<String, String>>()

		Position.values().toList().forEach { data : Position ->
			var position = HashMap<String, String>()
			position.put("value", data.type)
			position.put("label", data.desc)
			positions.add(position)
		}
		if (positions.isEmpty()) throw CustomException("Positions data not found", HttpStatus.NOT_FOUND)
		return positions
	}

	@Throws(CustomException::class)
	fun getBanks(servlet: HttpServletRequest): ArrayList<Map<String, String>> {
		var banks = ArrayList<Map<String, String>>()

		BankCode.values().toList().forEach { data : BankCode ->
			var bank = HashMap<String, String>()
			bank.put("value", data.type)
			bank.put("label", data.desc)
			banks.add(bank)
		}
		if (banks.isEmpty()) throw CustomException("Positions data not found", HttpStatus.NOT_FOUND)
		return banks
	}

}
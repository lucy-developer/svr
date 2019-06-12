package io.eb.svr.handler.service

import io.eb.svr.exception.CustomException
import io.eb.svr.model.enums.AreaGroup
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

/**
 * Create by lucy on 2019-06-11
 **/
@Service
class CoreDataService {

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

}
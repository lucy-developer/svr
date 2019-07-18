package io.eb.svr.common.util

import org.hibernate.HibernateException
import org.hibernate.Session
import org.hibernate.SessionFactory
import javax.persistence.EntityManager

/**
 * Create by lucy on 2019-07-17
 **/
object HibernateUtil {
	@Throws(HibernateException::class)
	fun getSession(entityManager: EntityManager): Session {
		val sessionFactory = entityManager.entityManagerFactory.unwrap(SessionFactory::class.java)

		return try {
			sessionFactory.currentSession
		} catch (he: HibernateException) {
			sessionFactory.openSession()
		}
	}
}
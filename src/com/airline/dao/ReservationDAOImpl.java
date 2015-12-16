package com.airline.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.airline.model.ReservationDO;

/**
 * reservation DAO implementation logic APIs
 * @author
 *
 */
public class ReservationDAOImpl {

	private SessionFactory sessionFactory;
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;		
	}

	/**
	 * Method to save the reservation into the database
	 * @param reservationDO
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public Integer saveReservation(ReservationDO reservationDO) {
		Session session = this.sessionFactory.getCurrentSession();
		session.saveOrUpdate(reservationDO);
		return reservationDO.getId();
	}
}

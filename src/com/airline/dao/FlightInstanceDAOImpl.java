package com.airline.dao;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.airline.model.FlightDO;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import com.airline.model.FlightInstanceDO;

/**
 * Flight instance DAO implementation logic apis
 * @author
 *
 */
public class FlightInstanceDAOImpl {
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	/**
	 * Method to get the flight instances from the database based on departure date
	 * @param departureDate
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public Set<FlightInstanceDO> getFlightInstance(Date departureDate) {
		Session session = this.sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(FlightInstanceDO.class);
		//matching the input departure date
		java.sql.Date departure = new java.sql.Date(departureDate.getTime());
		criteria.add(Restrictions.eq("departureDateTime", departure));
		Set<FlightInstanceDO> list = new HashSet(criteria.list());
		return list;
	}

	/**
	 * API to get all the flights based on input flight number
	 *
	 * @param flightNumber
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public FlightInstanceDO getFlight(String flightNumber) {
		Session session = this.sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(FlightInstanceDO.class);
		criteria.add(Restrictions.eq("id", Integer.parseInt(flightNumber)));
		criteria.setFetchSize(1);
		FlightInstanceDO flightInstanceDO = (FlightInstanceDO) criteria.uniqueResult();
		return flightInstanceDO;
	}
}

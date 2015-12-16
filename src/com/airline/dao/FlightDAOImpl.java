package com.airline.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import com.airline.model.FlightDO;

/**
 * Flight DAO implementation logic APIs
 *
 * @author
 */
public class FlightDAOImpl {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    /**
     * API to get all the flights based on input flight number
     *
     * @param flightNumber
     * @return
     */
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public FlightDO getFlight(String flightNumber) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(FlightDO.class);
        criteria.add(Restrictions.eq("flightNumber", flightNumber));
        criteria.setFetchSize(1);
        FlightDO flightDO = (FlightDO) criteria.uniqueResult();
        return flightDO;
    }
}

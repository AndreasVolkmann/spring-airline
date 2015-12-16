/**
 * 
 */
package com.airline.service.response;

import java.io.Serializable;
import java.util.Set;

/**
 * @author
 *
 */
public class InfoResponse implements Serializable {

	String airline;
	Set<Flight> flights;

	public Set<Flight> getFlights() {
		return flights;
	}

	public void setFlights(Set<Flight> flights) {
		this.flights = flights;
	}

	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}
	
	
}

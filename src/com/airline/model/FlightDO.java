package com.airline.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


/**
 * @author 
 *
 */
@Entity
@Table(name="FLIGHT")
public class FlightDO  implements Serializable{

	@Id
	@Column(name="FLIGHTNUMBER")
	private String flightNumber;
	
	@Column(name="AIRLINENAME")
	private String airlineName;
	
	@Column(name="NUMBEROFSEATS")
	private Integer numberOfSeats;

	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "FLIGHT_FLIGHTINSTANCE",  joinColumns = { 
			@JoinColumn(name = "Flight_FLIGHTNUMBER", nullable = false, updatable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "flightInstances_ID", 
					nullable = false, updatable = false) })
	public Set<FlightInstanceDO> flightInstanceDOSet;

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getAirlineName() {
		return airlineName;
	}

	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}

	public Integer getNumberOfSeats() {
		return numberOfSeats;
	}

	public void setNumberOfSeats(Integer numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	public Set<FlightInstanceDO> getFlightInstanceDOSet() {
		return flightInstanceDOSet;
	}

	public void setFlightInstanceDOSet(Set<FlightInstanceDO> flightInstanceDOSet) {
		this.flightInstanceDOSet = flightInstanceDOSet;
	}
	
}

package com.airline.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


/**
 * Flight instance DO class
 * @author 
 *
 */
@Entity
@Table(name="FLIGHTINSTANCE")
public class FlightInstanceDO  implements Serializable{

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="AVAILABLESEATS")
	private Integer availableSeats;
	
	@Column(name="DEPARTUREDATETIME")
	private Date departureDateTime;
	
	@Column(name="FLIGHTTIME")
	private Integer flightTime;
	
	@Column(name="PRICE")
	private Double price;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(targetEntity=ArrivalsDO.class)  
	@JoinColumn(name="FlightInstance_ID")
	private Set<ArrivalsDO> arrivalSet;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(targetEntity=DepartureDO.class)  
	@JoinColumn(name="FlightInstance_ID")
	private Set<DepartureDO> departureSet;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "FLIGHT_FLIGHTINSTANCE",  joinColumns = { 
			@JoinColumn(name = "flightInstances_ID", nullable = false, updatable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "Flight_FLIGHTNUMBER", 
					nullable = false, updatable = false) })
	public Set<FlightDO> flightDOSet;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAvailableSeats() {
		return availableSeats;
	}

	public void setAvailableSeats(Integer availableSeats) {
		this.availableSeats = availableSeats;
	}

	public Date getDepartureDateTime() {
		return departureDateTime;
	}

	public void setDepartureDateTime(Date departureDateTime) {
		this.departureDateTime = departureDateTime;
	}

	public Integer getFlightTime() {
		return flightTime;
	}

	public void setFlightTime(Integer flightTime) {
		this.flightTime = flightTime;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Set<ArrivalsDO> getArrivalSet() {
		return arrivalSet;
	}

	public void setArrivalSet(Set<ArrivalsDO> arrivalSet) {
		this.arrivalSet = arrivalSet;
	}

	public Set<DepartureDO> getDepartureSet() {
		return departureSet;
	}

	public void setDepartureSet(Set<DepartureDO> departureSet) {
		this.departureSet = departureSet;
	}

	public Set<FlightDO> getFlightDOSet() {
		return flightDOSet;
	}

	public void setFlightDOSet(Set<FlightDO> flightDOSet) {
		this.flightDOSet = flightDOSet;
	}
}

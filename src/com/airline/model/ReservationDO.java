package com.airline.model;

import java.io.Serializable;
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
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


/**
 * Reservation DO class
 * @author 
 *
 */
@Entity
@Table(name="RESERVATION")
public class ReservationDO  implements Serializable{

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="PRICE")
	private Double price;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "RESERVATION_PASSENGER",  joinColumns = { 
			@JoinColumn(name = "Reservation_ID", nullable = false, updatable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "passengers_ID", 
					nullable = false, updatable = false) })
	public Set<PassengerDO> passengerDOSet;

	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "FLIGHTINSTANCE_RESERVATION",  joinColumns = { 
			@JoinColumn(name = "reservations_ID", nullable = false, updatable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "FlightInstance_ID", 
					nullable = false, updatable = false) })
	public Set<FlightInstanceDO> flightInstanceDOSet;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Set<PassengerDO> getPassengerDOSet() {
		return passengerDOSet;
	}

	public void setPassengerDOSet(Set<PassengerDO> passengerDOSet) {
		this.passengerDOSet = passengerDOSet;
	}

	public Set<FlightInstanceDO> getFlightInstanceDOSet() {
		return flightInstanceDOSet;
	}

	public void setFlightInstanceDOSet(Set<FlightInstanceDO> flightInstanceDOSet) {
		this.flightInstanceDOSet = flightInstanceDOSet;
	}

}

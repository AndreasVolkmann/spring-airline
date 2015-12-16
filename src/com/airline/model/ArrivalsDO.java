package com.airline.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @author 
 *
 */
@Entity
@Table(name="ARRIVALS")
public class ArrivalsDO implements Serializable{

	@Id
	@Column(name="FlightInstance_ID")
	private Integer flightInstanceID;
	
	@Id
	@Column(name="from_IATACODE")
	private String fromIATACode;

	public Integer getFlightInstanceID() {
		return flightInstanceID;
	}

	public void setFlightInstanceID(Integer flightInstanceID) {
		this.flightInstanceID = flightInstanceID;
	}

	public String getFromIATACode() {
		return fromIATACode;
	}

	public void setFromIATACode(String fromIATACode) {
		this.fromIATACode = fromIATACode;
	}
	
	
}

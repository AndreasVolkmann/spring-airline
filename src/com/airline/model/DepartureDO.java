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
@Table(name="DEPARTURES")
public class DepartureDO  implements Serializable{

	@Id
	@Column(name="FlightInstance_ID")
	private Integer flightInstanceID;
	
	@Id
	@Column(name="to_IATACODE")
	private String toIATACode;

	public Integer getFlightInstanceID() {
		return flightInstanceID;
	}

	public void setFlightInstanceID(Integer flightInstanceID) {
		this.flightInstanceID = flightInstanceID;
	}

	public String getToIATACode() {
		return toIATACode;
	}

	public void setToIATACode(String toIATACode) {
		this.toIATACode = toIATACode;
	}
	
	
}

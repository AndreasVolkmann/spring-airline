/**
 * 
 */
package com.airline.service.request;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.airline.service.common.Passenger;

/**
 * @author
 *
 */
public class ReservationRequest {

	String flightID;
	Integer numberOfSeats;
	@JsonProperty("ReservePhone")
	String reservePhone;
	@JsonProperty("ReserveeEmail")
	String reserveeEmail;
	@JsonProperty("ReserveeName")
	String reserveeName;
	@JsonProperty("Passengers")
	List<Passenger> passengers;
	public String getFlightID() {
		return flightID;
	}
	public void setFlightID(String flightID) {
		this.flightID = flightID;
	}
	public Integer getNumberOfSeats() {
		return numberOfSeats;
	}
	public void setNumberOfSeats(Integer numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}
	public String getReservePhone() {
		return reservePhone;
	}
	public void setReservePhone(String reservePhone) {
		this.reservePhone = reservePhone;
	}
	public String getReserveeEmail() {
		return reserveeEmail;
	}
	public void setReserveeEmail(String reserveeEmail) {
		this.reserveeEmail = reserveeEmail;
	}
	public String getReserveeName() {
		return reserveeName;
	}
	public void setReserveeName(String reserveeName) {
		this.reserveeName = reserveeName;
	}
	public List<Passenger> getPassengers() {
		return passengers;
	}
	public void setPassengers(List<Passenger> passengers) {
		this.passengers = passengers;
	}
	
	
}

/**
 * 
 */
package com.airline.service.response;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.airline.service.common.Passenger;

/**
 * @author
 *
 */
public class ReservationResponse implements Serializable{

	String flightID;
	String Origin;
	String Destination;
	String Date;
	Integer FlightTime;
	Integer numberOfSeats;
	String ReserveeName;
	List<Passenger> Passengers;
	public String getFlightID() {
		return flightID;
	}
	public void setFlightID(String flightID) {
		this.flightID = flightID;
	}
	public String getOrigin() {
		return Origin;
	}
	public void setOrigin(String origin) {
		Origin = origin;
	}
	public String getDestination() {
		return Destination;
	}
	public void setDestination(String destination) {
		Destination = destination;
	}
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	public Integer getFlightTime() {
		return FlightTime;
	}
	public void setFlightTime(Integer flightTime) {
		FlightTime = flightTime;
	}
	public Integer getNumberOfSeats() {
		return numberOfSeats;
	}
	public void setNumberOfSeats(Integer numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}
	public String getReserveeName() {
		return ReserveeName;
	}
	public void setReserveeName(String reserveeName) {
		ReserveeName = reserveeName;
	}
	public List<Passenger> getPassengers() {
		return Passengers;
	}
	public void setPassengers(List<Passenger> passengers) {
		Passengers = passengers;
	}
	
	
}

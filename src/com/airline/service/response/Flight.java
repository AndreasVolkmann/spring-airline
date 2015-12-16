/**
 * 
 */
package com.airline.service.response;

import java.io.Serializable;
import java.util.Date;

/**
 * @author
 *
 */
public class Flight implements Serializable {

	String date;
	Integer numberOfSeats;
	Double priceTotal;
	String flightId;
	Integer travelTime;
	String destination;
	String origin;
	
	public Flight(String date,Integer numberOfSeats,Double priceTotal,String flightId,Integer travelTime,
		String destination,String origin){
		this.date = date;
		this.numberOfSeats = numberOfSeats;
		this.priceTotal = priceTotal;
		this.flightId = flightId;
		this.travelTime = travelTime;
		this.destination = destination;
		this.origin = origin;
		
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Integer getNumberOfSeats() {
		return numberOfSeats;
	}
	public void setNumberOfSeats(Integer numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}
	public Double getTotalPrice() {
		return priceTotal;
	}
	public void setTotalPrice(Double totalPrice) {
		this.priceTotal = totalPrice;
	}
	public String getFlightID() {
		return flightId;
	}
	public void setFlightID(String flightID) {
		this.flightId = flightID;
	}
	public Integer getTraveltime() {
		return travelTime;
	}
	public void setTraveltime(Integer traveltime) {
		this.travelTime = traveltime;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	
}

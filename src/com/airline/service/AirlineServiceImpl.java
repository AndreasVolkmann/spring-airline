package com.airline.service;

import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.airline.controller.exception.FlightException;
import com.airline.dao.FlightDAOImpl;
import com.airline.dao.FlightInstanceDAOImpl;
import com.airline.dao.ReservationDAOImpl;
import com.airline.model.ArrivalsDO;
import com.airline.model.DepartureDO;
import com.airline.model.FlightDO;
import com.airline.model.FlightInstanceDO;
import com.airline.model.PassengerDO;
import com.airline.model.ReservationDO;
import com.airline.service.common.Passenger;
import com.airline.service.request.ReservationRequest;
import com.airline.service.response.Flight;
import com.airline.service.response.InfoResponse;
import com.airline.service.response.ReservationResponse;
import com.airline.utils.DateUtil;

/**
 * Main login class container 3 APIS
 * API 1 - To get one way flights
 * API 2 - To get two way flights
 * API 3 - To reserve a flight
 * @author
 *
 */
@Service
public class AirlineServiceImpl  {
	
	private FlightInstanceDAOImpl flightInstanceDAOImpl = null;
	private FlightDAOImpl flightDAOImpl = null;
	private ReservationDAOImpl reservationDAOImpl = null;
	
	/**
	 * API to get one way flights
	 * @param from
	 * @param date
	 * @param numTickets
	 * @return
	 * @throws FlightException
	 */
	@Transactional(readOnly=true)
	public InfoResponse getOneWayFlights(String from,String date,Integer numTickets) throws FlightException {
		if(null == from || from.trim().length()==0)
			throw new FlightException(400,3, "Origin not provided");
		if(null == numTickets || numTickets <=0)
			throw new FlightException(400,3, "Number of tickets not provided");

		InfoResponse infoResponse = new InfoResponse();
		try {
			Date departureDate = DateUtil.toDate(date);
			boolean seatsAvailable = true;
			Set<FlightInstanceDO> flightInstanceDOSet = flightInstanceDAOImpl.getFlightInstance(departureDate);
			//check all flights flying on asked date
			for(FlightInstanceDO flightInstanceDO : flightInstanceDOSet){
				DepartureDO departureDO = null != flightInstanceDO.getDepartureSet() && !flightInstanceDO.getDepartureSet().isEmpty() ?
						flightInstanceDO.getDepartureSet().iterator().next(): null;
				ArrivalsDO arrivalsDO = null != flightInstanceDO.getArrivalSet() && !flightInstanceDO.getArrivalSet().isEmpty() ?
						flightInstanceDO.getArrivalSet().iterator().next(): null;
				FlightDO flightDO = null != flightInstanceDO.getFlightDOSet() && !flightInstanceDO.getFlightDOSet().isEmpty() ?
						flightInstanceDO.getFlightDOSet().iterator().next(): null;
				//if origin is same
				if(null != departureDO && null != arrivalsDO && arrivalsDO.getFromIATACode().equalsIgnoreCase(from)){
					//check seat available
					if(flightInstanceDO.getAvailableSeats() >= numTickets){
						//calculate price
						Double price = numTickets * flightInstanceDO.getPrice();
						infoResponse.setAirline(flightDO.getAirlineName());
						//prepare response
						Flight flight = new Flight(date,numTickets,price,
								flightInstanceDO.getId().toString(), flightInstanceDO.getFlightTime(), departureDO.getToIATACode(), from);
						if(infoResponse.getFlights() == null)
							infoResponse.setFlights(new HashSet<Flight>());
						infoResponse.getFlights().add(flight);
						seatsAvailable = true;
					}else
						seatsAvailable = false;
				}
			}
			if(!seatsAvailable){
				throw new FlightException(404,2, "Not much enough seats available for date "+ departureDate.toString());
			}
			if(null == infoResponse.getFlights() || infoResponse.getFlights().isEmpty()){
				throw new FlightException(404, 1, "No matching flights for date "+ departureDate.toString());
			}
		} catch (ParseException e) {
			throw new FlightException(400,3, "Input date is not properly ISO 8601 format "+ date);
		}
		return infoResponse;
	}
	
	/**
	 * API to get two way flights
	 * @param from
	 * @param to
	 * @param date
	 * @param numTickets
	 * @return
	 * @throws FlightException
	 */
	public InfoResponse getTwoWayFlights(
			String from,String to,String date,Integer numTickets) throws FlightException {
		if(null == from || from.trim().length()==0)
			throw new FlightException(400,3, "Origin not provided");
		if(null == to || to.trim().length()==0)
			throw new FlightException(400,3, "Destination not provided");
		if(null == numTickets || numTickets <=0)
			throw new FlightException(400,3, "Number of tickets not provided");

		InfoResponse infoResponse = new InfoResponse();
		try {
			Date departureDate = DateUtil.toDate(date);
			boolean seatsAvailable = true;
			Set<FlightInstanceDO> flightInstanceDOSet = flightInstanceDAOImpl.getFlightInstance(departureDate);
			//check all flights flying on asked date
			for(FlightInstanceDO flightInstanceDO : flightInstanceDOSet){
				DepartureDO departureDO = null != flightInstanceDO.getDepartureSet() && !flightInstanceDO.getDepartureSet().isEmpty() ?
						flightInstanceDO.getDepartureSet().iterator().next(): null;
				ArrivalsDO arrivalsDO = null != flightInstanceDO.getArrivalSet() && !flightInstanceDO.getArrivalSet().isEmpty() ?
						flightInstanceDO.getArrivalSet().iterator().next(): null;
				FlightDO flightDO = null != flightInstanceDO.getFlightDOSet() && !flightInstanceDO.getFlightDOSet().isEmpty() ?
						flightInstanceDO.getFlightDOSet().iterator().next(): null;
				//if origin is same
				if(null != departureDO && null != arrivalsDO && departureDO.getToIATACode().equalsIgnoreCase(to)
						&& arrivalsDO.getFromIATACode().equalsIgnoreCase(from)){
					//check seat available
					if(flightInstanceDO.getAvailableSeats() >= numTickets){
						//calculate price
						Double price = numTickets * flightInstanceDO.getPrice();
						infoResponse.setAirline(flightDO.getAirlineName());

						//prepare response
						Flight flight = new Flight(date,numTickets,price,
								flightDO.getFlightNumber(),flightInstanceDO.getFlightTime(),to, from);
						if(infoResponse.getFlights() == null)
							infoResponse.setFlights(new HashSet<Flight>());
						infoResponse.getFlights().add(flight);
						seatsAvailable = true;
					}else
						seatsAvailable = false;
				}
			}
			if(!seatsAvailable){
				throw new FlightException(404,2, "Not much enough seats available for date "+ departureDate.toString());
			}
			if(null == infoResponse.getFlights() || infoResponse.getFlights().isEmpty()){
				throw new FlightException(404, 1, "No matching flights for date "+ departureDate.toString());
			}
		} catch (ParseException e) {
			throw new FlightException(400,3, "Input date is not properly ISO 8601 format "+ date);
		}
		return infoResponse;
	}
	
	/**
	 * API to reserve a flight
	 * @param reservationRequest
	 * @return
	 * @throws FlightException
	 */
	public ReservationResponse reserveFlight(ReservationRequest reservationRequest) throws FlightException {
		//validate all input parameters
		if(null == reservationRequest)
			throw new FlightException(400,3, "Reservation request not provided");
		if(null == reservationRequest.getFlightID() || reservationRequest.getFlightID().trim().isEmpty())
			throw new FlightException(400,3, "FlightId not provided");
		if(null == reservationRequest.getNumberOfSeats() || reservationRequest.getNumberOfSeats() <= 0)
			throw new FlightException(400,3, "Number of seats should be integer and greater than zero");
		if(null == reservationRequest.getReserveeName() || reservationRequest.getReserveeName().trim().isEmpty())
			throw new FlightException(400,3, "Reserve name not provided");
		if(null == reservationRequest.getPassengers() || reservationRequest.getPassengers().isEmpty())
			throw new FlightException(400,3, "Passengers list are not provided");
		
		//FlightDO flightDO = flightDAOImpl.getFlight(reservationRequest.getFlightID());
		//if(null == flightDO || null == flightDO.getFlightInstanceDOSet() || flightDO.getFlightInstanceDOSet().isEmpty())
		//	throw new FlightException(404,1, "No flights with id "+reservationRequest.getFlightID());

		FlightInstanceDO flightInstanceDO = flightInstanceDAOImpl.getFlight(reservationRequest.getFlightID());
		//validate enough seats are left for reservation
		if(flightInstanceDO.getAvailableSeats() < reservationRequest.getNumberOfSeats())
			throw new FlightException(404,1, "Not much enough seats available for flight "+reservationRequest.getFlightID());
		
		//reduce the booked seats from available number
		flightInstanceDO.setAvailableSeats(flightInstanceDO.getAvailableSeats() - reservationRequest.getNumberOfSeats());
		
		//creating reservation dataobject for saving to database
		ReservationDO reservationDO = new ReservationDO();
		reservationDO.setPrice(flightInstanceDO.getPrice() * reservationRequest.getNumberOfSeats());
		reservationDO.setFlightInstanceDOSet(new HashSet<FlightInstanceDO>(1));
		reservationDO.getFlightInstanceDOSet().add(flightInstanceDO);
		//fetch all passenger lists
		for(Passenger passenger : reservationRequest.getPassengers()){
			if(null == reservationDO.getPassengerDOSet())
				reservationDO.setPassengerDOSet(new HashSet<PassengerDO>());
			reservationDO.getPassengerDOSet().add(
					new PassengerDO(passenger.getFirstName(),passenger.getLastName()));
		}
		//saving the servation into the database
		reservationDAOImpl.saveReservation(reservationDO);
		
		//preparing the reservation response
		ReservationResponse reservationResponse  = new ReservationResponse();
		reservationResponse.setFlightID(reservationRequest.getFlightID());
		reservationResponse.setDestination(flightInstanceDO.getDepartureSet().iterator().next().getToIATACode());
		reservationResponse.setOrigin(flightInstanceDO.getArrivalSet().iterator().next().getFromIATACode());
		reservationResponse.setFlightTime(flightInstanceDO.getFlightTime());
		reservationResponse.setDate(DateUtil.formatDate(new Date(flightInstanceDO.getDepartureDateTime().getTime())));
		reservationResponse.setNumberOfSeats(reservationRequest.getNumberOfSeats());
		reservationResponse.setPassengers(reservationRequest.getPassengers());
		reservationResponse.setReserveeName(reservationRequest.getReserveeName());
		return reservationResponse;

	}
	/**
	 * 
	 * @param flightInstanceDAOImpl
	 */
	public void setFlightInstanceDAOImpl(FlightInstanceDAOImpl flightInstanceDAOImpl) {
		this.flightInstanceDAOImpl = flightInstanceDAOImpl;
	}
	
	/**
	 * 
	 * @param flightDAOImpl
	 */
	public void setFlightDAOImpl(FlightDAOImpl flightDAOImpl) {
		this.flightDAOImpl = flightDAOImpl;
	}
	
	/**
	 * 
	 * @param reservationDAOImpl
	 */
	public void setReservationDAOImpl(ReservationDAOImpl reservationDAOImpl) {
		this.reservationDAOImpl = reservationDAOImpl;
	}
	
}
